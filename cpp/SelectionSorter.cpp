/**
 * My second C++ program
 */

#include <iostream>
#include <cstdlib>
#include <vector>
#include <algorithm>
using namespace std;

void print_vector(const vector<int>& v) {
  vector<int>::const_iterator const_iter;
  for (const_iter = v.begin(); const_iter != v.end(); const_iter++) {
    cout << *const_iter << endl;
  }
}

void swap_int(int* a, int* b) {
  int tmp = *a;
  *a = *b;
  *b = tmp;
}

void swap_cpp(int& a, int& b) {
  int tmp = a;
  a = b; 
  b = tmp;
}

// int a, b;
// swap_cpp(a, b);

void selection_sort(vector<int>* v_p) {
  for (int i = 0; i < v_p->size(); i++) {
    int min_value = INT_MAX;
    int min_index = 0;
    for (int j = i; j < v_p->size(); j++) {
      if ((*v_p)[j] < min_value) {
	min_value = (*v_p)[j];
	min_index = j;
      }
    }
    std::swap((*v_p)[i], (*v_p)[min_index]);
    cout << "After " << i << "th iteration, the min value is " << min_value << endl;
    print_vector((*v_p));
  }
}

int main() {
  vector<int> v;
  v.push_back(3);
  v.push_back(1);
  v.push_back(2);
  v.push_back(5);
  v.push_back(4);

  selection_sort(&v);
  cout << "The sorted vector is:" << endl;
  print_vector(v);
  return EXIT_SUCCESS;
}
