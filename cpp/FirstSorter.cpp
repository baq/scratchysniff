/**
 * My first sorting program
 */

#include <iostream>
#include <algorithm>
#include <iterator>

template <typename T = int>
struct my_functor {
  bool operator()(T a, T b) {
    return a < b;
  }
};

int main() {

  int numbers[10] = {2,1,5,3,9,6,23,124,98,47};

  std::copy(numbers, numbers+10,
	    std::ostream_iterator<int>(std::cout, " "));

  std::cout << std::endl;

  my_functor<> f;
  std::cout << 1 << " " << 2 << " " << f(1, 2) << std::endl;

  std::sort(numbers, numbers+10, f);

  std::copy(numbers, numbers+10,
	    std::ostream_iterator<int>(std::cout, " "));

  std::cout << std::endl;

}
