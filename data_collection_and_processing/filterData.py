import os

counts = {"O":0, "B":0, "A":0, "F":0, "G":0, "K":0, "M":0}

def viable(dataList):
  table = ["O", "B", "A", "F", "G", "K", "M"]
  for clss in table:
    if clss in dataList[0] and (counts[clss] < 100):
      counts[clss] += 1
      return clss
  return None



def filterData():
  path = "filtered_data_files"
  if not os.path.exists(path):
    os.makedirs(path)
  for filename in os.listdir("rel_data_files"):
    with open("rel_data_files/" + filename) as f:
      headers = f.readline()
      headersList = headers.split(", ")
      data = f.readline()
      dataList = data.split(", ")
      clss = viable(dataList)
      if(clss != None):
        newf = open(path + "/" + filename, 'wb')
        newf.write(headers)
        dataList = dataList[1:]
        newf.write(clss)
        for data in dataList:
          newf.write(", " + data)
        newf.flush()
        newf.close()


def main():
  filterData()

main()
