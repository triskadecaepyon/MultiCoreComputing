#include "omp.h"
#include <stdlib.h>
#include <stdio.h>
#define SEED 35791246
#define CHUNKSIZE 3

double MonteCarloPi(int s) {
// TODO: Implement your Monte Carlo Method
// parameter s: number of points you randomly choose
// return the value of pi
    int niter = s;
    double x, y;
    int i, count = 0; /* # of points in the 1st quadrant of unit circle */
    double z;
    double pi;

    /* initialize random numbers */
    srand(SEED);
    count = 0;
    int chunk = CHUNKSIZE;
#pragma omp parallel shared(chunk) private(i,x,y,z) reduction(+:count)
    {
	printf("NUMThreads: %d\n", omp_get_num_procs());
#pragma omp for schedule(dynamic,chunk)
        for (i = 0; i < niter; i++) {
            x = (double) rand() / RAND_MAX;
            y = (double) rand() / RAND_MAX;
            z = x * x + y * y;
            if (z <= 1) count++;
        }
    }
    pi = (double) count / niter * 4;
    printf("# of trials= %d , estimate of pi is %g \n", niter, pi);
    return pi;
}

int main() {
    int s = 50000;
    printf("Enter s: ");
    scanf("%d", &s);
    printf("S is %d\n", s);
    double pi = MonteCarloPi(s);
    printf("Pi is %lf.\n", pi);
    return 0;
}
