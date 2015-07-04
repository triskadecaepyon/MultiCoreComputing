//
// Created by DavidLiu on 7/3/15.
//

#include "matrix_mult.h"

#include <omp.h>
#include <stdio.h>

int main() {
    printf("hello world");
    printf("Number of processors: %d", omp_get_num_procs());
}