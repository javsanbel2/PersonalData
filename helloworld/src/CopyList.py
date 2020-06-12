'''
Created on 29 May 2020

@author: umer
'''
l1 = [1,2,3,4,58,9,5,8,2]
l2 = [2,7,8,96,2,9] 
def f1(q,w):
    w=list(set(q)-set(w))
    print(w)
f1(l1, l2)
def f2(a,b):
    for i in a:
        if i in b:
            b.remove(i)
        else:
            b.append(i)
    print(b)
f2(l1,l2)
    