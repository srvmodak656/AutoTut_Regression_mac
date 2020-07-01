""" This is a program which will manage the regression operation which will test if the
last build pushed to the repository is perfectly fine to be into final build of that version

AUTHOR:- SOURAV MODAK

30 June 2020

India
"""
import os
import threading
import time
from writeTut import runProgramRegression

stop_threads = False

def getFilePath(workspacePath, allowedProduct):
    """ This is the method which will help to get path of all the .b files which is needed to be tested
    As of now it will exculde the one which has more than 1000 lines of code and may be we will put a
    restriction in time of execution and if it takes more time than it then we will drop that and mmove
    forward to next .b

    workspacePath = path of the t24 test workspace in string format
    allowedProduct = list of all the components which will be allowed in the regression

    Return:-
    Will return the list of path of all the files which can be used to run the regression
    """
    outputList = list()
    for product in allowedProduct:
        targetPath = workspacePath+os.sep+product
        listOfFile = os.listdir(targetPath)
        for folderName in listOfFile:
            targetFolderPath = targetPath + os.sep + folderName
            if folderName.startswith(product):  # Assuming that the folder name will unanmiously always start with component name
                #print(targetFolderPath)
                fullPath = targetFolderPath + os.sep + "Source" + os.sep + "Private"
                try:
                    fileInside = os.listdir(fullPath)
                    for file in fileInside:
                        fullFilePath = fullPath + os.sep + file
                        outputList.append(fullFilePath)
                except:
                    print("No such path exist : " + fullPath)

    return outputList

def runRegression(filePathList):
    """ This will be used to run the regression using the file path list that we will obtain from getFilePath
    filePathList = list of all the filePath which will be run.
    This will run the regression and delete the .tut file from the test workspace file"""

    def runThread(stop,filePath):
        """ This is a thread run method which will be used to run the program"""
        runProgramRegression(filePath)
        if stop():
            return
    def timer():
        """ Thread to put a timer"""
    for filePath in filePathList:
        stop_threads = False
        t1 = threading.Thread(target=runThread, args=(lambda: stop_threads, filePath))
        time_start = time.time()
        t1.start()
        while t1.is_alive():
            if (time.time() - time_start) > 2:
                stop_threads = True
                t1.join()


"""allowedCompoent = list()
allowedCompoent.append("AA")
for file in getFileNames("/Users/souravmodak/Desktop/Dev_Workspace", allowedCompoent):
    print(file)"""
filePathList = list()
filePathList.append("/Users/souravmodak/Desktop/Dev_Workspace/AA/AA_PricingGrid/Source/Private/AA.PRICING.GRID.UPDATE.b")
runRegression(filePathList)