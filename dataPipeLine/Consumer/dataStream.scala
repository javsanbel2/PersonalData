package consumer.stockData

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext._
import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming._
import org.apache.spark.sql.types._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import java.sql.Timestamp
import com.mongodb.spark._
import com.mongodb.spark.config._
import scala.util.Try
import java.io.{File, PrintWriter}

object dataStream {
  
  //function to re-affirm dataType
  def checkData(df: DataFrame): DataFrame = {
      var tmpDf = df.schema("date_time").dataType match {
          case StringType => df.withColumn("date_time", ((col("date_time").cast("Long"))/1000000000).cast("timestamp"))
          case LongType => df.withColumn("date_time",(col("date_time")/1000000000).cast("timestamp"))
          case _ => df
      }
      tmpDf = tmpDf.schema("price").dataType match {
          case StringType => tmpDf.withColumn("price",col("price").cast("Double"))
          case _ => tmpDf
      }
      tmpDf = tmpDf.schema("exchange_id").dataType match {
          case StringType => tmpDf.withColumn("exchange_id",col("exchange_id").cast("Int"))
          case _ => tmpDf
      }
      tmpDf = tmpDf.schema("trade_size").dataType match {
          case StringType => tmpDf.withColumn("trade_size",col("trade_size").cast("Int"))
          case _ => tmpDf
      }
      return tmpDf.toDF
  }
  
  
  def main(args : Array[String]){
    val writer = new PrintWriter(new File("output.log"))
    writer.write("consumer log file\n")
    writer.write(java.time.LocalDate.now.toString()+"\n")
    var org = Logger.getLogger("org").setLevel(Level.ERROR)
    var kfk = Logger.getLogger("kafka").setLevel(Level.INFO)
    val spark: SparkSession = SparkSession.builder()
    .master("local[*]")
    .appName("kafkaStream")
    .getOrCreate()
    writer.write(org.toString()+"\n")
    import spark.implicits._
    //dummpy data for test
    val someDF = Seq(("LOLL",287.33,17,2, Timestamp.valueOf("2020-06-29 15:26:48"))).toDF("t", "p","x","s","dt")
          
    //reading data from spark and extracting required data
    val df = spark
      .read
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "stockData")
      .load()
    df.printSchema
    writer.write(kfk.toString()+"\n")
    
    //getting offset of last commited message
    val ofsetDF = df.select(($"offset").cast("int"))
    //drop all rows with null values
    val ofstTemp = ofsetDF.na.drop()
    // if null removes properly
    if(ofstTemp.count == ofsetDF.count){
        writer.write("error null rows in the dataset\n")
    }
    val y = ofstTemp.agg(max("offset")).take(1)
    for (data <- y){
       writer.write("\noffset of last message "+data(0).toString+"\n")
    }
    
    val selectDF = df.select(get_json_object(($"value").cast("string"),"$.data.T").alias("ticker"),
                             get_json_object(($"value").cast("string"),"$.data.p").alias("price"),
                             get_json_object(($"value").cast("string"),"$.data.x").alias("exchange_id"),
                             get_json_object(($"value").cast("string"),"$.data.s").alias("trade_size"),
                             get_json_object(($"value").cast("string"),"$.data.t").alias("date_time"))
    //drop all rows with null values
    val temp = selectDF.na.drop()
    
    val temp2 = checkData(temp)
    temp2.printSchema
    
    //testing with dummy data
    assert(someDF.schema("dt").dataType == temp2.schema("date_time").dataType)
    assert(someDF.schema("t").dataType == temp2.schema("ticker").dataType)
    assert(someDF.schema("p").dataType == temp2.schema("price").dataType)
    assert(someDF.schema("x").dataType == temp2.schema("exchange_id").dataType)
    assert(someDF.schema("s").dataType == temp2.schema("trade_size").dataType)

    val x = java.time.LocalDate.now
    //temp2.show
    
    val temp3 = temp2.filter(to_date(temp2("date_time")) === x.toString())
    //temp3.show
    val APPL = temp3.filter("ticker == 'AAPL'").sort(desc("date_time"))
    val MSFT = temp3.filter("ticker == 'MSFT'").sort(desc("date_time"))
    val TSLA = temp3.filter("ticker == 'TSLA'").sort(desc("date_time"))
    //adding data to mongoDB
    try{
      MongoSpark.save(APPL.write.mode("overwrite"), WriteConfig(Map("uri" -> "mongodb://127.0.0.1/mydb.APPL")))
      MongoSpark.save(MSFT.write.mode("overwrite"), WriteConfig(Map("uri" -> "mongodb://127.0.0.1/mydb.MSFT")))
      MongoSpark.save(TSLA.write.mode("overwrite"), WriteConfig(Map("uri" -> "mongodb://127.0.0.1/mydb.TSLA")))
    }catch{
      case e: com.mongodb.MongoSocketWriteException => writer.write("Error while ingesting data into mongo")
    }
    
    writer.close()
  }
}