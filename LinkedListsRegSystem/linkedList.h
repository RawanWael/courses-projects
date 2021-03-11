//
// Created by yassi on 15/11/2019.
//
//linkedList.h includes structs definitions and all the protypes for the functions described in linkedList.c
#ifndef UNTITLED19_LINKEDLIST_H
#define UNTITLED19_LINKEDLIST_H
typedef struct courseNode* ptrCourse;
typedef struct studentNode* ptrStudent;
typedef struct studentDemand* ptrDemand;
typedef struct studentInCourseNode* ptrStudentInCourse;
typedef ptrCourse courseLIST;
typedef ptrCourse coursePOSITION;
typedef ptrStudent studentLIST;
typedef ptrStudent studentPOSITION;
typedef ptrDemand demandLIST;
typedef ptrDemand demandPOSITION;
typedef ptrStudentInCourse studentInCourseLIST;
typedef ptrStudentInCourse studentInCoursePOSITION;
struct course
{
    char name[70];
    char id[20];
    int year;
    int start;
    int finish;
    int maxNumOfStudent;
    int currentNum;
};
struct courseNode
{
    struct course c;
    ptrStudentInCourse studentInCourseNext;
    ptrCourse courseNext;
};
struct studentNode
{
    char name[30];
    int id;
    int age;
    int numOfCourses;
    int currentNumOfCourse;
    ptrDemand demandNEXT; //creating the linked list for courses//
    ptrStudent studentNext;
};
struct studentDemand
{
    struct course c;
    int flag;//1 when student is registered successfully in  a course//
    ptrDemand demandNEXT;
};
struct studentInCourseNode
{
    char name[30];
    int id;
    int age;
    int lenOfname;
    ptrStudentInCourse studentInCourseNext;
};

int readCoursesFromFile(char[],courseLIST);
int readStudentsFromFile(char[],studentLIST);
void registration(courseLIST,studentLIST);
int valueTime(char []);
coursePOSITION isAvailableAndAllowed(demandPOSITION,studentPOSITION,courseLIST);
int timeConflict(demandPOSITION ,demandLIST);
void deleteACourse(demandPOSITION,demandLIST);
void addStudentToCourse(studentPOSITION,courseLIST,coursePOSITION,studentInCourseLIST);
void addCourse(courseLIST);
void addStudent(studentLIST);
void radixSortOfStudentsInCourses(courseLIST,coursePOSITION,char[],int);
int maxNumberOfLetters(coursePOSITION);
void addSpace(studentInCoursePOSITION,int);
void copyStudents(studentInCourseLIST,studentInCourseLIST);
int map(char);
studentInCoursePOSITION returnStudentForRadixSort(studentInCourseLIST);
void prepareForNewIteration(studentInCourseLIST [], studentInCourseLIST []);
void deletePocket(studentInCourseLIST []);
void scheduleOfAStudent(studentLIST,int);
void copyingForSchedule(demandLIST [],studentPOSITION);
void sortCoursesForSchedule(demandLIST [],int);
void sortCourse(courseLIST ,coursePOSITION );
coursePOSITION findCourse(courseLIST,char[]);
void deletCourseList(courseLIST);
void deletStudentList(studentLIST);
void preparingForReport1(courseLIST);
void report(char[],studentInCourseLIST [],coursePOSITION,int imp);
void preparingForReport2(courseLIST );
void preparingForReport3(studentLIST);
void preparingForReport4(courseLIST);
void menu();
#endif //UNTITLED19_LINKEDLIST_H
