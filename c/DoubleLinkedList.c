#include "DoubleLinkedList.h"
#include "LinkedList.h"
#include <cassert>
#include <cstdio>

DoubleEntry* convert(Entry* head_ll) {
  //Assert that the linked list has at least one entry
  assert(head_ll != NULL);

  // Create the first entry of the doubly linked list
  DoubleEntry* head_dl = (DoubleEntry*) malloc(sizeof(DoubleEntry));
  // Assign the previous pointer of the doubly linked list to NULL
  head_dl->prev_ptr == NULL;
  // Copy the value of the first linked list entry into the first
  // entry of the doubly linked list
  head_dl->value = head_ll->value;

  // Create a placeholder for the current entry of the doubly
  // linked list
  DoubleEntry* curr_dl = head_dl;
  // Loop through the remaining elements of the linked list,
  // copying the data into the doubly linked list
  Entry* curr_ll = head_ll->next_ptr;
  while (curr_ll != NULL) {
    // Create the next new entry of the doubly linked list
    DoubleEntry* next_dl = (DoubleEntry*) malloc(sizeof(DoubleEntry));
    // Set the next pointer of our current entry to the new entry
    curr_dl->next_ptr = next_dl;
    // Set the previous pointer of the new entry to the current entry
    next_dl->prev_ptr = curr_dl;
    // Copy the value of the current linked list entry into the new
    // entry of the doubly linked list
    next_dl->value = curr_ll->value;
    // Set the current entry to the new one.
    curr_dl = next_dl;
    // Advance the traversal of the linked list
    curr_ll = curr_ll->next_ptr;
  }
  // Set the next pointer of the last entry of the doubly linked
  // list to null
  curr_dl->next_ptr = NULL;

  return head_dl;
}
