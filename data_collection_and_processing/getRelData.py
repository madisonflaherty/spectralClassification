"""
Takes in a filename as an argument and pulls it apart to collect relevant data.
Generates a new file in 'refined_data' directory under same name, with:
  - subclass
  - spectrometry data
"""
from astropy.io import fits
import os
import sys

"""
Notes:
Line Measurement Information is in hdulist[3].data

Flux measuremnts are found in hdulist[1].data['flux']  << returned as numpy array

"""



def openFITSFile(filename):
  hdulist = fits.open(filename)
  return hdulist

def getFluxData(hdulist):
  tbdata = hdulist[1].data
  fluxData = tbdata['flux']
  waveLengthAxis = tbdata['loglam']
  pair = (waveLengthAxis, fluxData)
  return pair

def getStarSubClass(hdulist):
  tbdata = hdulist[2].data
  subclass = tbdata['SUBCLASS'][0]
  return subclass

def generateNewFile(name, subclass, fluxPair):
  (waveLengthAxis, fluxData) = fluxPair
  path = 'rel_data_files'
  if not os.path.exists(path):
        os.makedirs(path)

  filename = name + '-data.csv'
  f = open(os.path.join(path, filename), 'w')

  # header of csv file
  f.write("subclass")
  for wave in waveLengthAxis:
    f.write(", " + str(10**wave))
  f.write('\n')
  # data of csv file
  f.write(subclass)
  for flux in fluxData:
    f.write(", " + str(flux))
  f.write('\n')
  f.flush()
  f.close()


def main():
  filename = sys.argv[1]
  hdulist = openFITSFile(filename)
  name = os.path.basename(filename)[:-5]
  fluxPair = getFluxData(hdulist)
  subclass = getStarSubClass(hdulist)
  generateNewFile(name, subclass, fluxPair)
  hdulist.close()

main()
