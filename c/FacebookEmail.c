#include <cstdio>
#include <cassert>
#include <iostream>

int main() {
  signed int x = 0xFACEB00C >> 2;
  printf("size of x is %d\n", sizeof(x));
  std::cout << "x is " << x << std::endl;
  return EXIT_SUCCESS;
}
