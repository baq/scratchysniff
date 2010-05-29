/**
 * My first C++ program
 */
#include <iostream> 
#include <cstdlib>

namespace bauki {
  static const char* kMessage = "Hello World";
}

int main() {
  using namespace std;
  cout << bauki::kMessage << endl;
  return EXIT_SUCCESS;
}
