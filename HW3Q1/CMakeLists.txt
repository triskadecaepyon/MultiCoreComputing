cmake_minimum_required(VERSION 3.2)
project(HW3Q1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES)
add_executable(HW3Q1 ${SOURCE_FILES} matrix_mult.h matrix_mult_new.cpp)