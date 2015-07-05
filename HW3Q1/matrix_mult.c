//
// Created by DavidLiu on 7/3/15.
//

#include "matrix_mult.h"

#include <omp.h>
#include <stdio.h>
#include <stdbool.h>

int array_A_size[];
int array_B_size[];

bool find_array_size(FILE *file_pointer, int array_size_final[]) {
    int rowsize;
    int columnsize;
    rowsize = fgetc(file_pointer) - '0';
    fgetc(file_pointer); //grabs the space
    columnsize = fgetc(file_pointer) - '0';
    printf("Array size: %i x %i \n", rowsize, columnsize);
    if (rowsize != NULL && columnsize != NULL){
        // printf("Size is good \n");
        array_size_final[0] = rowsize;
        array_size_final[1] = columnsize;
        return true;
    } else {
        return false;
    }
}


int main( int argc, char *argv[] ) {
    // Determine if all the necessary arguments are there
    if (argc < 3) {
        printf("Need all 3 arguments");
        return 0;
    } else {
        printf("File 1: %s, ", argv[1]);
        printf("File 2: %s, ", argv[2]);
        printf("Threads: %s \n", argv[3]);
    }

    FILE *file_one = fopen( argv[1], "r" );
    if ( file_one == 0 ) {
        printf( "Issue opening first file\n" );
    } else {
        bool result = find_array_size(file_one, array_A_size);
        if (result) {
            printf("Size is good \n");
        }
        //Parse the rest of the array now that you know the size
        fclose(file_one);
    }

    FILE *file_two = fopen( argv[2], "r" );
    if ( file_two == 0 ) {
        printf( "Issue opening first file\n" );
    } else {
        bool result = find_array_size(file_two, array_B_size);
        if (result) {
            printf("Size is good \n");
        }
        //Parse the rest of the array now that you know the size
        fclose( file_two );
    }
#pragma omp parallel
    {
        printf("Hello world test!\n");
        printf("Number of processors: %d \n", omp_get_num_procs());
    }
    return 0;
}
