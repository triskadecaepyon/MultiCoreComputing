//
// Created by Yinzhe Lu on 7/7/15.
//

#include "matrix_mult.h"

#include <omp.h>
#include <stdio.h>
#include <stdbool.h>
#include <fstream>
#include <sstream>

void print_matrix(double *matrix, int m, int n){
	for (int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
		printf("%lf ", *((matrix+i*n) + j));
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
	return false;
}

void matrixPop(int row, int col, double* matrix, char* arg){

    std::string line;
    std::ifstream infileOne(arg);
    double first_matrix[row][col];
    for(int i = 0; i < row; i++){
	std::getline(infileOne, line);
        std::istringstream iss(line);
        for(int j = 0; j < col; j++){
	    iss >> first_matrix[i][j];
	}
    }
    matrix = (double*)first_matrix;
    print_matrix(matrix, row, col);
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

    if (!(iss_one >> ROWA >> COLA)) { 
	printf("Something bad happened!");
	return 0; 
    } // error
    printf("%d, %d\n", ROWA, COLA);
    matrixPop(ROWA, COLA, A, argv[1]);

    std::getline(infileTwo, line);
    std::istringstream iss_two(line);

    if (!(iss_two >> ROWB >> COLB)) { 
	printf("Something bad happened!");
	return 0; 
    } // error
    
    matrixPop(ROWB, COLB, B, argv[2]);   
#pragma omp parallel
    {
        printf("Hello world test!\n");
        printf("Number of processors: %d \n", omp_get_num_procs());
    }
    return 0;
}
