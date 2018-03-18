#!/usr/bin/env python
#-*- coding:utf8 -*-
'''
Created on 2016年5月20日

@author: sun
'''

import os,shutil

# 获取当前目录，并替换windows下的路径‘\\’为‘/’
parent_dir = os.getcwd().replace('\\', '/')
print "parent_dir = " + parent_dir

# 获取父目录
parent_dir_s = os.path.dirname(parent_dir)
print "parent_dir_s = " + parent_dir_s

def move(path):
    fileList = os.listdir(path)
    for file in fileList:
        if "_" in file and os.path.isdir(path + "/" + file):
            print parent_dir
            shutil.move(path + "/" + file, parent_dir_s + '/allin_ui_results/test-output/' + file)

move(parent_dir + "/test-output")
