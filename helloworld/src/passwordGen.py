'''
Created on 29 May 2020

@author: umer
'''
import random

print('''Password Generator''')

chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@£$%^&*().,?0123456789'

number = input('number of passwords?')
number = int(number)

length = input('password length?')
length = int(length)

print('\n here are your passwords:')

for pwd in range(number):
    password = ''
    for c in range(length):
        password += random.choice(chars)
    print(password)