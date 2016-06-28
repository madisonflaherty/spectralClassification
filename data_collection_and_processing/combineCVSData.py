import os
import sys
path = "filtered_data_files"

def determineRange():
  minRange = sys.float_info.max
  minfile = ""
  maxRange = sys.float_info.min
  maxfile = ""
  for filename in os.listdir(path):
    with open(path + "/" + filename) as f:
      headers = f.readline()
      headers = headers.split(", ")
      tempMin = float(headers[1])
      tempMax = float(headers[-1].rstrip())
      if tempMin < minRange:
        minRange = tempMin
        minfile = filename
      if tempMax > maxRange:
        maxRange = tempMax
        maxfile = filename
  return ((minRange, maxRange), (minfile, maxfile))

def generateHeader():
  (vals, files) = determineRange()
  (minRange, maxRange) = vals
  (minfile, maxfile) = files

  with open(path + "/" + minfile) as f:
    minheader = f.readline().split(", ")[1:]
  with open(path + "/" + maxfile) as f:
    maxheader = f.readline().split(", ")[1:]
  counter = 0
  finalHeader = []
  while(minheader[counter] != maxheader[0]):
    finalHeader.append(minheader[counter])
    counter += 1
  for elem in maxheader:
    finalHeader.append(elem)
  return finalHeader


def generateNEWCSVFile(header):
  filename = "combinedData.csv"
  f = open(filename, 'wb')
  # write header
  f.write("subclass")
  for h in header:
    f.write(", " + h)

  # data for each file
  for filename in os.listdir(path):
    with open(path + "/" + filename, "r") as f2:
      h2 = f2.readline().split(", ")
      data = f2.readline().split(", ")
      f.write(data[0])
      counter = 0
      # fill in lesser unknown data
      while(h2[1] != header[counter]):
        f.write(", 0")
        counter += 1
      # fill in known data
      for elem in data[1:]:
        f.write(", " + elem.strip("\n"))
        counter += 1
      emptys = len(header) - counter
      # fill in greater unknown data
      for i in range(emptys):
        f.write(", 0")
      f.write("\n")
      f.flush()
  f.close()

def main():
  header = generateHeader()
  generateNEWCSVFile(header)
main()
