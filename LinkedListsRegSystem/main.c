/*
Student: Rawan Yassin 1182224
Instructor: Dr.Iyad Jaber
The Registration application, is a simple application that reads an unspecified number of students and courses from two different files,
and then adds the student to the demanded courses by him/her after checking many condtions.
 */
#include <stdio.h>
#include <stdlib.h>
#include "linkedList.h"
void main() {
    //Creating the header for course list and getting the name of the file from which to read data
    courseLIST courseList=(courseLIST)malloc(sizeof(struct courseNode));
    char fileName[20];
    printf("\t\t\t\t\t\t\t\tWelcome to The Registration System\n");
    printf("Before start using the system, please do what you are asked for in the following two sentences.\n");
    printf("Enter file name to read courses, please add (.txt) at the end of its name\n");
    scanf("%s",fileName);
    while(readCoursesFromFile(fileName,courseList)==0){
        printf("Please re-enter file name, add (.txt) at the end of its name\n");
        scanf("%s",fileName);
    }
    //Creating header for the student's list and getting the name of the file from which to read data
    studentLIST  studentList=(studentLIST)malloc(sizeof(struct studentNode));
    printf("Enter file name to read students, please add (.txt) at the end of its name\n");
    scanf("%s",fileName);
    while(readStudentsFromFile(fileName,studentList)==0){
        printf("Please re-enter file name, add (.txt) at the end of its name\n");
        scanf("%s",fileName);
    }
    registration(courseList,studentList);//Calling the registration method
    char ch2;
    int ch;
    do {
        // system("cls");
        menu();
        scanf(" %c", &ch2);
        ch = ch2 - 48;
        switch (ch) {
            case 1: {
                preparingForReport1(courseList);
                break;
            }

            case 2: {
                preparingForReport2(courseList);
                break;
            }
            case 3: {
                preparingForReport3(studentList);
                break;
            }
            case 4: {
                preparingForReport4(courseList);
                break;
            }
            case 5:{
                addStudent(studentList);
            }
            default:
                continue;
        }
    } while (ch != 6);

//free the allocated memory for the linked lists
    deletCourseList(courseList);
    deletStudentList(studentList);
    exit(0);
}

