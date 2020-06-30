""" This is a program which will manage the regression operation which will test if the
last build pushed to the repository is perfectly fine to be into final build of that version

AUTHOR:- SOURAV MODAK

30 June 2020

India
"""

def getFileNames(workspacePath, allowedComponents):
    """ This is the method which will help to get path of all the .b files which is needed to be tested
    As of now it will exculde the one which has more than 1000 lines of code and may be we will put a
    restriction in time of execution and if it takes more time than it then we will drop that and mmove
    forward to next .b"""