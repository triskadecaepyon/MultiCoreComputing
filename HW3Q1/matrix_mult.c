//
// Created by DavidLiu on 7/3/15.
//

#include "matrix_mult.h"

#include <omp.h>
#include <stdio.h>

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
    if ( file_one == 0 )
        {
            printf( "Issue opening first file\n" );
        } else {
            int x;
            // Temporary file reading system placeholder
            while  ( ( x = fgetc( file_one ) ) != EOF ) {
                printf( "%c", x );
            }
            fclose( file_one );
    }
#pragma omp parallel
    {
        printf("hello world test\n");
        printf("Number of processors: %d \n", omp_get_num_procs());
    }
    return 0;
}