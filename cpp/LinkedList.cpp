struct Node {
  Node* next_ptr;
  int value;
}

class LinkedList {
public:
  add(const int& value);
  remove(const Node& node);
}
