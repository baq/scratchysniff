#include "LinkedList.h"

struct DoubleEntry {
  int value;
  DoubleEntry* next_ptr;
  DoubleEntry* prev_ptr;
};

/**
 * Takes a singly linked list and returns a doubly linked
 * list.
 * @param   Entry* the pointer to the head of the linked list
 * @returns DoubleEntry* the pointer to the head of the doubly
 *                     linked list.
 */
DoubleEntry* convert(Entry* head_ll);
