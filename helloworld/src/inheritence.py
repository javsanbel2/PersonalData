'''
Created on 29 May 2020

@author: umer
'''
class human:
    def __init__(self, name, profession, skype, school):
        self.name = name
        self.profession = profession
        self.skype = skype
        self.school = school
        
    def show(self):
        return 'name= ' + self.name + '\nporfession= ' + self.profession + '\nskype= ' + self.skype +'\nschool= ' +self.school

class student(human):
    def __init__(self, name, profession, skype, school,hobby):
        super().__init__(name, profession, skype, school)
        self.hobby =hobby
        
    def show(self):
        return super().show() + '\nhobby= ' + self.hobby + '\n'

class teacher(human):
    def __init__(self, name, profession, skype, school, course1, course2):
        super().__init__(name, profession, skype, school)
        self.course1 =course1
        self.course2 =course2
        
    def show(self):
        return super().show() + '\nClass= ' + self.course1
            
frida = student('farida','student','farida96','MAH','hikiing')
akdr = teacher('jabkni','researcher','skyname','MAH','CS1','CS2')
print(frida.show())
print(akdr.show())