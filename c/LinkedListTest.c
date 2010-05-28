#include "LinkedList.h"
#include "DoubleLinkedList.h"
#include <cassert>
#include <cstdio>

int testLinkedListGeneral() {
  Entry* head_ptr = construct(1);
  addBack(head_ptr, 2);
  assert(length(head_ptr) == 2);
  addBack(head_ptr, 3);
  assert(length(head_ptr) == 3);
  return 1;
}

int testLinkedListSizeOne() {
  Entry* head_ptr = construct(1);
  assert(head_ptr != NULL);
  assert(length(head_ptr) == 1);
  return 1;
}
int testEmptyLinkedList() {
  Entry* head_ptr = NULL;
  assert(head_ptr == NULL);
  assert(length(head_ptr) == 0);
  return 1;
}

int testLinkedListBackRemoval() {
  Entry* head_ptr = construct(1);
  int value = removeBack(&head_ptr);
  assert(head_ptr == NULL);
  assert(value == 1);

  head_ptr = construct(1);
  addBack(head_ptr, 2);
  value = removeBack(&head_ptr);
  assert(head_ptr != NULL);
  assert(head_ptr->next_ptr == NULL);
  assert(value == 2);
   
  return 1;
}

int testLinkedListFrontRemoval() {
  Entry* head_ptr = construct(1);
  int value = removeBack(&head_ptr);
  assert(head_ptr == NULL);
  assert(value == 1);

  head_ptr = construct(1);
  addBack(head_ptr, 2);
  value = removeFront(&head_ptr);
  assert(head_ptr != NULL);
  assert(head_ptr->next_ptr == NULL);
  assert(value == 1);

  return 1;
}

int testConversion() {
  Entry* head_ll = construct(1);
  DoubleEntry* head_dl = convert(head_ll);
  assert(head_dl != NULL);
  assert(head_dl->prev_ptr == NULL);
  assert(head_dl->next_ptr == NULL);
  assert(head_dl->value == 1);

  addBack(head_ll, 2);
  head_dl = convert(head_ll);
  assert(head_dl != NULL);
  assert(head_dl->prev_ptr == NULL);
  assert(head_dl->next_ptr != NULL);
  assert(head_dl->value == 1);
  assert(head_dl->next_ptr->value == 2);

  addBack(head_ll, 3);
  head_dl = convert(head_ll);
  assert(head_dl != NULL);
  assert(head_dl->prev_ptr == NULL);
  assert(head_dl->next_ptr != NULL);
  assert(head_dl->value == 1);
  assert(head_dl->next_ptr->value == 2);
  assert(head_dl->next_ptr->next_ptr != NULL);
  assert(head_dl->next_ptr->next_ptr->value == 3);

  return 1;
}

int testReversal() {
  Entry* head_ptr = construct(1);
  addBack(head_ptr, 2);
  addBack(head_ptr, 3);

  reverse(&head_ptr);

  assert(head_ptr != NULL);
  assert(head_ptr->value == 3);
  assert(head_ptr->next_ptr != NULL);
  assert(head_ptr->next_ptr->value == 2);
  assert(head_ptr->next_ptr->next_ptr != NULL);
  assert(head_ptr->next_ptr->next_ptr->value == 1);
  assert(head_ptr->next_ptr->next_ptr->next_ptr == NULL);
  return 1;
}

int testEmptyListReversal() {
  Entry* head_ptr = NULL;
  reverse(&head_ptr);
  assert(head_ptr == NULL);
  return 1;
}

int testOneListReversal() {
  Entry* head_ptr = construct(1);
  reverse(&head_ptr);
  assert(head_ptr != NULL);
  assert(head_ptr->value == 1);
  assert(head_ptr->next_ptr == NULL);
  return 1;
}

int main() {
  /*assert(testEmptyLinkedList() == 1);
  assert(testLinkedListSizeOne() == 1);
  assert(testLinkedListGeneral() == 1);
  assert(testLinkedListBackRemoval() == 1);
  assert(testLinkedListFrontRemoval() == 1);
  assert(testConversion());*/
  assert(testReversal());
  assert(testEmptyListReversal());
  assert(testOneListReversal());
  printf("Test Succeeded!\n");
  return EXIT_SUCCESS;
}
