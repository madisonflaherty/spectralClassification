# Spectral Classification Of Stars By Spectrophotometry
The following project is an exploration of different data mining techniques. The project uses data pulled from the [SLOAN Digital Sky Survey](http://www.sdss.org) where each data point is derived from the supplied FITS files ([example](http://dr9.mirror.sdss3.org/sas/dr9/sdss/spectro/redux/26/spectra/0489/spec-0489-51930-0136.fits)). A more thorough explanation of the project as of May 20, 2015 can be found in the research paper in this repository titled
[SpectralClassificationofStarsBySpectrophotometry](https://github.com/madisonflaherty/spectralClassification/blob/master/SpectralClassificationofStarsBySpectrophotometry.pdf). Below is a simplified (but likely more up-to-date) discussion of the project in its current state.

## The Problem
Multiple approaches to classifying stars using only the detailed spectrophotometric wavelengths procured from the SLOAN Digital Sky Survey. Stars are classified according to their spectral types (O,B,A,F,G,K,M). Further subclassing (i.e. a MIV type star is planned but not yet implemented).

## Approaches
### Principal Components Analysis
The spectral data pulled from the FITS files each contain at least 5000 attributes. When dealing with needing to train increasingly large datasets, a way to reduce the number of attributes was required to make the problem more manageable and scaleable. PCA was applied to the original dataset and the testing dataset using WEKAs built in PCA processor. This process brought the original attribute size from 5000 attributes to just two. While this is incredibly exciting, the built in
WEKA software is not built to process large files and restricts the datasize to be extremely small (less that 10,000 data points) to be processed. Due to this limitation, a PCA implementation will be developed with greater scalability in mind. 
### Artificial Neural Nets
As of today, only a single perceptron implementation has been completed and tested. This implementation simply trained the perceptron to answer "yes" or "no" on whether a given data point was a type X star. In production tests, seven perceptrons are generated and the perceptron with the most confidence determined the classification. The ANN currently has an average accuracy of approximately 70%. 
### Decision Trees
##### Gini Indexing and Binary Splits
Unlike the ANN, the decision tree is able to output the given classification of star (rather than the binary answer of "yes" or "no". The decision tree with Gini indexing had an average accuracy of approximately 70.5%. 
### K-Nearest Neighbors
By far the easiest method to understand and implement, KNN was implemented as a simple base of comparison. Multiple iterations of the [Generalized Minkowski Norm](https://en.wikipedia.org/wiki/Minkowski_distance) were attempted with different values of K (the number of neighbors to count) and P, the *exponent* in the Generalized Minkowski Norm. The best combination of P and K values has so far been found to be P=4 and K=9. In this particular case, the average accuracy was shown to be
approximately 71.3%. 


## Future
The following is a list of tasks (in no particular order) that I am interested in delving more deeply. 
- As stated previously, PCA needs to be implemented with a scalable structure to allow much larger data sets to be restructured. 
- A fully realized neural net featuring at the very least back propogation.
- Testing different distance metrics.
    - cosine similarity or the mahalanobis distance might prove more successful at classification. 
    - Better structuring and automation scripts. 
    - Multiway splits in decision trees.
    - Dedicated testing framework.
    - etc
