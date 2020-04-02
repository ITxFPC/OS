# coding=utf-8
import threading
import numpy as np
import time
# 建立global參數
list = []

# 費氏數列
def fibonacci(n):
    if n == 0 or n == 1:
        return (n)
    else:
        return (fibonacci(n - 1) + fibonacci(n - 2))

# define child thread job
def job():
    global list
    for i in range(10):
        list.append(fibonacci(i))

# create a child thread
t = threading.Thread(target = job)

# execute child tread
t.start()

# execute parent thread job
for i in range(5):
    print("Waiting Parent thread to finish: %d second left" % (5-i))
    time.sleep(1)
# join thread
t.join()

# print result
print(list)
