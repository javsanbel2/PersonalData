'''
Created on 29 May 2020

@author: umer
'''
import re
patterns = [ 'fox', 'dog', 'horse' ]
text = 'The quick brown fox jumps over the lazy dog.'
for pattern in patterns:
    if re.search(pattern,  text):
        print('Matched!', pattern)
    else:
        print('Not Matched!')