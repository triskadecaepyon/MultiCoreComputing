//
// Created by Yinzhe Lu on 7/7/15.
//

#include "matrix_mult.h"

#include <omp.h>
#include <stdio.h>
#include <stdbool.h>
#include <fstream>
#include <sstream>
#include <cstdlib>

void print_matrix(double *matrix, int m, int n){
	for (int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
		printf("%f ", matrix[i*n+j]);
	    }
            printf("\n");
	}
}

bool MatrixMult(int rowA, int colA, double* A, int rowB, int colB, double* B,
double* C, int T) {
// TODO: Implement your parallel multiplication for two matrices of doubles
// by using OpenMP
// parameter rowA: indicates the number of rows of the matrix A
// parameter colA: indicates the number of columns of the matrix A
// parameter A: indicates the matrix A
// parameter rowB: indicates the number of rows of the matrix B
// parameter colB: indicates the number of columns of the matrix B
// parameter B: indicates the matrix B
// parameter C: indicates the matrix C, which is the results of A x B
// parameter T: indicates the number of threads
// return true if A and B can be multiplied; otherwise, return false
        printf("In function\n");
        int i, j, k;
	if(colA != rowB) // incompatible matrix dimensions!
	{
	   printf("Incompatible Matrix Dimensions!\n");
	   return false;
	}
	#pragma omp parallel num_threads(T) shared(A, B, C) private(i, j, k)
	{
	#pragma omp for schedule(static)
		for (i = 0 ; i < rowA; i++)
		{
			for(j = 0; j < colB; j++)
			{
		   		double sum = 0;
		   		for (k = 0; k < colA; k++) 
				{
	          			 sum = sum + A[i*colA+k] * B[k*colB+j];
        			}
			   	C[i*colB+j] = sum;
			}
		}
	}
	return true;
}

void matrixPop(int row, int col, double* matrix, char* arg){

    std::string line;
    std::ifstream infileOne(arg);
    std::getline(infileOne, line);
    for(int i = 0; i < row; i++){
        std::getline(infileOne, line);
        std::istringstream iss(line);
        for(int j = 0; j < col; j++){
	   iss >> matrix[i*col+j];
	}
    }
    return;
}

int main( int argc, char *argv[] ) {
    int ROWA;
    int COLA;
    int ROWB;
    int COLB;
    int T;
    double* A;
    double* B;
    double* C;
    // Determine if all the necessary arguments are there
    if (argc < 3) {
        printf("Need all 3 arguments");
        return 0;
    } else {
        printf("File 1: %s, ", argv[1]);
        printf("File 2: %s, ", argv[2]);
        printf("Threads: %s \n", argv[3]);
    }
    
    std::ifstream infileOne(argv[1]);
    std::ifstream infileTwo(argv[2]);

    std::string line;
    std::getline(infileOne, line);
    std::istringstream iss_one(line);

    T = atoi(argv[3]);
    if (!(iss_one >> ROWA >> COLA)) { 
	printf("Something bad happened!");
	return 0; 
    } // error
    printf("%d, %d\n", ROWA, COLA);
    A = new double[ROWA*COLA];
    matrixPop(ROWA, COLA, A, argv[1]);
    print_matrix(A, ROWA, COLA);

    std::getline(infileTwo, line);
    std::istringstream iss_two(line);

    if (!(iss_two >> ROWB >> COLB)) { 
	printf("Something bad happened!");
	return 0; 
    } // error

    B = new double[ROWB*COLB];
    matrixPop(ROWB, COLB, B, argv[2]);
    print_matrix(B, ROWB, COLB);
    C = new double[ROWA*COLB];
    MatrixMult(ROWA, COLA, A, ROWB, COLB, B, C, T);
    print_matrix(C, ROWA, COLB);
    return 0;
}
