#include "LinkedList.h"
#include <cassert>
#include <cstdio>

Entry* construct(int value) {
  Entry* entry_ptr = (Entry*) malloc(sizeof(Entry));
  entry_ptr->value = value;
  entry_ptr->next_ptr = NULL;
  return entry_ptr;
}

void addBack(Entry* list_ptr, int value) {
  assert(list_ptr != NULL);
  Entry* traverse_ptr = list_ptr;
  for (; traverse_ptr->next_ptr != NULL; 
       traverse_ptr = traverse_ptr->next_ptr) {}
  traverse_ptr->next_ptr = construct(value);
}

Entry* addFront(Entry* list_ptr, int value) {
  assert(list_ptr != NULL);
  Entry* new_entry_ptr = construct(value);
  new_entry_ptr->next_ptr = list_ptr;
  return new_entry_ptr;
}

void inc(int* x) {
  (*x)++;
}

int removeFront(Entry** list_ptr_ptr) {
  Entry* list_ptr = *list_ptr_ptr;
  assert(list_ptr != NULL);

  // Grab the return value off of the head entry
  int returnValue = list_ptr->value;

  // Copy the location of the head entry
  Entry* temp_ptr = list_ptr;
  // Assign the new head of the list to the value
  // following the head entry
  *list_ptr_ptr = list_ptr->next_ptr;
  // Free memory used by the old head entry
  free(temp_ptr);

  // Return the return value
  return returnValue;
}

int removeBack(Entry** list_ptr_ptr) {
  Entry* list_ptr = *list_ptr_ptr;
  assert(list_ptr != NULL);
  // This special case handles linked lists that have
  // only one entry
  if (list_ptr->next_ptr == NULL) {
    int returnValue = list_ptr->value;
    free(*list_ptr_ptr);
    *list_ptr_ptr = NULL;
    return returnValue;
  }
  
  // The prev_ptr will be a placeholder for the second to the
  // last entry in the linked list
  Entry* prev_ptr = NULL;
  // The traverse pointer will travel the linked list
  Entry* traverse_ptr = list_ptr; 
  // Traverse until we've reached the end of the list
  while (traverse_ptr->next_ptr != NULL) {
    // The traveler is not yet at the end of the list,
    // so assign it to prev_ptr
    prev_ptr = traverse_ptr;
    // Increment the traveller 
    traverse_ptr = traverse_ptr->next_ptr;
  }
  // At this point, traverse pointer will be the last
  // entry in the list and prev_ptr will be the entry
  // before it.
  int returnValue = prev_ptr->next_ptr->value;
  free(prev_ptr->next_ptr);
  prev_ptr->next_ptr = NULL;
  return returnValue;
}

Entry* reverse(Entry** list_ptr_ptr) {
  Entry* list_ptr = *list_ptr_ptr;
  if (list_ptr == NULL) {
    return list_ptr;
  }

  // A linked list with only one entry is the reverse
  // of itself.
  if (list_ptr->next_ptr == NULL) {
    return list_ptr;
  }

  // Remove the head of the list
  int frontValue = removeFront(list_ptr_ptr);
  // Call reverse on the tail
  Entry* last_element = reverse(list_ptr_ptr);
  // Construct the new last element of the list
  Entry* new_last_element = construct(frontValue);
  // Add the new last element to the end of the list
  last_element->next_ptr = new_last_element;
  // Return the new last element
  return new_last_element;
}


int length(Entry* list_ptr) {
  int size = 0;
  for (Entry* traverse_ptr = list_ptr; 
       traverse_ptr != NULL; 
       traverse_ptr = traverse_ptr->next_ptr) {
    size++;
  }
  return size;
}

void print(Entry* list_ptr) {
  for (Entry* traverse_ptr = list_ptr; 
       traverse_ptr != NULL; 
       traverse_ptr = traverse_ptr->next_ptr) {
    printf("%d->", traverse_ptr->value);
  }
}


