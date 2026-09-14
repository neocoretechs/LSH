<h3>Locality Sensitive Hash (LSH) Demonstrator</h3>
An Index contains one or more locality sensitive hash tables. These hash
tables contain the mapping between a combination of a number of hashes
(encoded using an integer) and a list of possible nearest neighbors.
HashFunctions:<p/>
Manhattan:<p/> This distance measure calculates the city block distance between two vectors. This distance metric also known as the Manhattan distance or L<sub>1</sub> distance.
Wikipedia calls it <a href="http://en.wikipedia.org/wiki/Taxicab_geometry">"Taxicab geometry"</a>:
<blockquote> 
Taxicab geometry, considered by Hermann Minkowski in the 19th 
century, is a form of geometry in which the usual distance function or metric
of Euclidean geometry is replaced by a new metric in which the distance
between two points is the sum of the absolute differences of their 
coordinates.<p>
The taxicab metric is also known as rectilinear distance, L1
distance or norm (see Lp space), city block distance, Manhattan distance, or
Manhattan length, with corresponding variations in the name of the
geometry. The latter names allude to the grid layout of most streets on
the island of Manhattan, which causes the shortest path a car could take
between two intersections in the borough to have length equal to the
intersections' distance in taxicab geometry. 
</blockquote>
<p/>
Cosine:<p/>
<blockquote> 
In cosine hashing, the goal is to map similar vectors (in terms of cosine similarity) to the same or nearby hash buckets 
 with high probability. The hash function is designed such that the probability of two vectors being mapped to the same 
bucket is proportional to their cosine similarity.<p>
 Given two vectors x and y, the cosine similarity is defined as:<br>
 cos(x, y) = (x · y) / (||x|| ||y||) <br>
  where x · y is the dot product of x and y, and ||x|| and ||y|| are the magnitudes (norms) of x and y, respectively. <p>
  In cosine hashing, we use a random projection vector w to compute the hash value. Specifically, the hash function is defined as:<br>
  h(x) = sign(w · x) <br>
  where sign is a function that returns 1 if the dot product is positive and 0 otherwise. <p>
  Relation of distance to number of hashes: <br>
  The key idea behind LSH is that if two vectors are similar (i.e., have high cosine similarity), 
  they are more likely to be mapped to the same bucket. The probability of two vectors being mapped to the same bucket is given by: <br>
  P(h(x) = h(y)) = 1 - (θ(x, y) / π) <br>
  where θ(x, y) is the angle between x and y.<p>
  To increase the accuracy of the similarity search, we use multiple hash functions (i.e., multiple random projection vectors w).<p>
  The number of hashes required to achieve a certain level of accuracy depends on the desired similarity threshold and the 
  dimensionality of the data. <p>
  In general, the more hashes we use, the more accurate the similarity search will be. However, using too many hashes can lead to 
  increased computational cost and storage requirements.<p>
  Trade-off:<br>
  There is a trade-off between the number of hashes and the accuracy of the similarity search.<p> 
  Increasing the number of hashes improves the accuracy but also increases the computational cost and storage requirements.<br>
  In practice, the number of hashes is typically chosen based on the specific requirements of the application, such as the 
  desired level of accuracy, the size of the dataset, and the available computational resources.<p>
  By using multiple hash functions and combining the results, LSH can efficiently identify similar vectors in high-dimensional space, 
  making it a powerful technique for similarity search and clustering applications.
  </blockquote>
  <p/>
  Euclidian:
  <p/>
  <blockquote> 
 Calculates the straight line
 between two vectors. Sometimes this is also called the L<sub>2</sub>
 distance.
  </blockquote>
  