#ifndef LINKEDLIST_H
#define LINKEDLIST_H
/**
 * An entry in a linked list, consisting of an int value and
 * a pointer to the next entry in the linked list.
 */
struct Entry {
  int value;
  struct Entry* next_ptr;
};

/** 
 * Creates and returns a pointer to a new linked list entry whose
 * value is the value specified and whose next pointer is null.
 */
Entry* construct(int value);

/**
 * Adds an entry to the end of the linked list
 * @param list_ptr the pointer to the start of the linked list
 * @param value the value to be added to the list
 **/
void addBack(Entry* list_ptr, int value);

/**
 * Adds an entry to the front of the list, returns the new head of the list
 * @param list_ptr the pointer to the start of the linked list
 * @param value the value to be the added to the list
 * @returns the new head of the list
 */
Entry* addFront(Entry* list_ptr, int value);

/**
 * Removes the first entry in the list and returns the removed entry.
 * @param list_ptr the pointer to the start of the linked list
 * @returns the value stored in the first entry in the list
 */
int removeFront(Entry** list_ptr_ptr);

/**
 * Removes the last entry in the list, returns the removed entry.  If only
 * one entry remains in the list, that entry will be removed and the list_ptr
 * will point to NULL.
 * @param list_ptr the pointer to the start of the linked list
 * @returns the value stored in the last entry in the list
 */
int removeBack(Entry** list_ptr_ptr);

/**
 * Reverses a linked list in place.
 * @param list_ptr_ptr the pointer to the head pointer of the list.
 * @returns the last element of the list.
 */
Entry* reverse(Entry** xlist_ptr_ptr);

/**
 * Returns the length of the linked list
 * @param list_ptr the pointer to the start of the linked list
 * @returns the length
 **/
int length(Entry* list_ptr);

/**
 * Prints the linked list to standard out
 * @param list_ptry the pointer to the start of the linked list
 **/
void print(Entry* list_ptr);

#endif
