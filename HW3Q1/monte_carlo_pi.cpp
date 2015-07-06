#include "omp.h"
#include <stdio.h>
#include <time.h>
#include <random>
#include <stdlib.h>

double MonteCarloPi(int s) {
// TODO: Implement your Monte Carlo Method
// parameter s: number of points you randomly choose
// return the value of pi
   double x,y;
   int i,count=0; /* # of points in the 1st quadrant of unit circle */
   double pi;
   long seed = 35791246;

   /* initialize random numbers */
   srand(seed);
   count=0;
   #pragma omp for schedule(static) // Schedule parallel threads for random point generation
   for ( i=0; i<s; i++) {
      x = (double)rand()/RAND_MAX; 
      y = (double)rand()/RAND_MAX;
      if (x*x+y*y<=1) count++; // Determine if points are within circle
   
   pi=(double)count/s*4; // Calculate Pi based on number of points in circle
   return pi;
}

int main(){
	int s = 10000;
	printf("Enter s: ");
	scanf("%d", &s);
	printf("S is %d\n", s);
	double pi = MonteCarloPi(s);
	printf("Pi is %lf.\n", pi);
	return 0;
}
