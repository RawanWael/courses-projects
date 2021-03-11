//
// Created by yassi on 15/11/2019.
//
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "linkedList.h"
//readCoursesFromFile function, reads the courses from the file, and stores them in a linked list, a course is added in an ascending order on year registration
int readCoursesFromFile(char fileName[],courseLIST courseList){
    char read[150];
    coursePOSITION new;
    if(courseList==NULL)
        exit(0);
    else {
        courseList->courseNext=NULL;
        FILE *in = fopen(fileName, "r");
        if(in==NULL){
            printf("Wrong File Name!\n");
            return 0;
        }
        while (fgets(read, sizeof(read), in)) {
            new = (coursePOSITION) malloc(sizeof(struct courseNode));
            new->courseNext=NULL;
            if(new!=NULL) {
                char *token = strtok(read, "#");
                strcpy((new->c.name), token);
                token = strtok(NULL, "#");
                strcpy((new->c.id), token);
                token = strtok(NULL, "#");
                int t = atoi(token);
                new->c.year = t;
                char *token1 = strtok(NULL, "#");
                token = strtok(NULL, "#");
                char *token3 = strtok(NULL, "#");
                t = valueTime(token1);
                new->c.start = t;
                t = valueTime(token);
                new->c.finish = t;
                if(new->c.finish-new->c.start<0){
                 t=new->c.finish;
                    new->c.finish=new->c.start;
                    new->c.start=t;
                }
                if(new->c.finish-new->c.start==0){
                    new->c.finish=new->c.start+50;
                }
                t = atoi(token3);
                new->c.maxNumOfStudent = t;
                new->c.currentNum = 0;
                studentInCourseLIST studentInCourseList = (studentInCourseLIST) malloc(sizeof(struct studentInCourseNode));
                new->studentInCourseNext = studentInCourseList;
                studentInCourseList->studentInCourseNext=NULL;
                sortCourse(courseList,new);
            }
        }
        fclose(in);
        printf("DONE READING\n");
    }
    return 1;
}
//readStudentsFromFile function, it reads the students and stores them in a linked list
int readStudentsFromFile(char fileName[],studentLIST studentList){
    char read[150];
    studentList->studentNext=NULL;
    studentPOSITION temp,new;
    int t;
    int i;
    FILE *inp = fopen(fileName, "r");
    if(inp==NULL){
        printf("Wrong File Name!\n");
        return 0;
    }
    if(studentList!=NULL) {
        temp = studentList;
        while (fgets(read, sizeof(read), inp)) {
            new = (studentPOSITION) malloc(sizeof(struct studentNode));
            new->studentNext=NULL;
            char *token2 = strtok(read, "#");
            strcpy(new->name, token2);
            token2 = strtok(NULL, "#");
            t = atoi(token2);
            new->id = t;
            token2 = strtok(NULL, "#");
            t = atoi(token2);
            new->age = t;
            demandLIST l;
            demandPOSITION temp3, new3;
            token2 = strtok(NULL, "# ");
            i = 0;
            l = (demandLIST) malloc(sizeof(struct studentDemand));
            if(l!=NULL)
                new->demandNEXT = l;
            temp3 = l;
            while (token2 != NULL) {
                new3 = (demandPOSITION) malloc(sizeof(struct studentDemand));
                if(new3!=NULL) {
                    if (token2[strlen(token2) - 1] == '\n')
                        token2[strlen(token2) - 1] = '\0';
                    strcpy(new3->c.id, token2);
                    i++;
                    token2 = strtok(NULL, "#");
                    temp3->demandNEXT = new3;
                    temp3 = new3;
                }
            }
            temp3->demandNEXT = NULL;
            new->numOfCourses = i;
            new->currentNumOfCourse = 0;
            temp->studentNext = new;
            temp = new;
        }
        printf("DONE READING\n");
    }
    fclose(inp);
    return 1;
}
//registration function, it takes the course and student lists, then adds the students to the courses according to various conditions.
void registration(courseLIST courseList,studentLIST studentList){
    studentPOSITION student;
    student=studentList->studentNext;
    demandPOSITION course;
    demandLIST l;
    coursePOSITION coursetest;
    coursetest=(coursePOSITION)malloc(sizeof(struct courseNode));
    if(coursetest!=NULL) {
        while (student != NULL) {
            l = student->demandNEXT;
            course=student->demandNEXT->demandNEXT;
            while ((course != NULL) && (student->currentNumOfCourse <= 5)) {
                coursetest = isAvailableAndAllowed(course, student, courseList);
                if (coursetest == NULL) {
                    course->flag=0;
                    course = course->demandNEXT;
                    continue;
                }
                if (timeConflict(course, l) == 0) {
                    course->flag=0;
                    course = course->demandNEXT;
                    continue;
                }
                if(student->currentNumOfCourse>5){
                    course->flag=0;
                    course = course->demandNEXT;
                    continue;
                }
                strcpy(course->c.name, coursetest->c.name);
                course->c.year = coursetest->c.year;
                course->flag=1;
                course->c.start = coursetest->c.start;
                course->c.finish = coursetest->c.finish;
                (student->currentNumOfCourse)++;
                addStudentToCourse(student, courseList, coursetest, coursetest->studentInCourseNext);
                course = course->demandNEXT;
            }
            student = student->studentNext;
        }
    }
}
//this function convert given time for a course to interger
int valueTime(char t[]){
    char toBeConverted[8];
    char *trim=strtok(t,":");
    strcpy(toBeConverted,trim);
    trim=strtok(NULL," ");
    strcat(toBeConverted,trim);
    int timeConverted=atoi(toBeConverted);
    return (timeConverted);

}
//this function checks if a given course is available in the list and if yes, checks if a student is allowed to register in based on his/her id and the registration year of course
coursePOSITION isAvailableAndAllowed(demandPOSITION course,studentPOSITION student,courseLIST courseList){
    coursePOSITION temp=courseList->courseNext;
    int t;
    while (temp!=NULL){
        if((strcmp(temp->c.id,course->c.id)==0)&&(temp->c.currentNum<temp->c.maxNumOfStudent)){
            t=(temp->c.year)*10000+9999;
            if(student->id<=t) {//check the registration year//
                return temp;
            }
            else {
                return NULL;
            }
        }
        temp=temp->courseNext;
    }
    return temp;
}
//To check whether there exists a time conflict or not in a student's schedule//
int timeConflict(demandPOSITION p,demandLIST l3){
    demandPOSITION temp;
    temp=l3->demandNEXT;
    if(temp==p){
        return 1;
    }
    while((temp!=NULL)&&(temp!=p)){
        if(temp->flag!=0) {
            if ((p->c.start > temp->c.start) && (p->c.start < temp->c.finish)) {
                return 0;
            }
            if ((p->c.start == temp->c.start) || p->c.finish == temp->c.finish) {
                return 0;
            }
        }
        temp=temp->demandNEXT;
    }
    return  1;
}
//this function adds student to the course after making sure that the student is allowed to register for this course and it adds the student in ascending order considering students' id
void addStudentToCourse(studentPOSITION student,courseLIST courseList,coursePOSITION coursetest,studentInCourseLIST l4) {
    coursePOSITION findCourse=courseList->courseNext;
    while((findCourse!=NULL)&&(strcmp(findCourse->c.id,coursetest->c.id)!=0)){
        findCourse=findCourse->courseNext;
    }
    studentInCoursePOSITION temp;
    temp = findCourse->studentInCourseNext;
    findCourse->c.currentNum++;
    if (temp->studentInCourseNext == NULL) {
        studentInCoursePOSITION new = (studentInCoursePOSITION) malloc(sizeof(struct studentInCourseNode));
        if(new!=NULL) {
            strcpy(new->name, student->name);
            new->id = student->id;
            new->age = student->age;
            new->lenOfname = strlen(new->name);
            temp->studentInCourseNext = new;
            new->studentInCourseNext = NULL;
        }
    }
    else if (temp->studentInCourseNext->studentInCourseNext == NULL) {
        if (student->id > temp->studentInCourseNext->id) {
            studentInCoursePOSITION new = (studentInCoursePOSITION) malloc(sizeof(struct studentInCourseNode));
            if(new!=NULL) {
                strcpy(new->name, student->name);
                new->id = student->id;
                new->age = student->age;
                new->lenOfname = strlen(new->name);
                temp->studentInCourseNext->studentInCourseNext = new;
                new->studentInCourseNext = NULL;
            }
        }
        else {
            studentInCoursePOSITION new = (studentInCoursePOSITION) malloc(sizeof(struct studentInCourseNode));
            if(temp!=NULL) {
                strcpy(new->name, student->name);
                new->id = student->id;
                new->age = student->age;
                new->lenOfname = strlen(new->name);
                new->studentInCourseNext = temp->studentInCourseNext;
                temp->studentInCourseNext = new;
                new->studentInCourseNext->studentInCourseNext = NULL;
            }
        }
    }
    else {
        while ((temp->studentInCourseNext != NULL) && (student->id > temp->studentInCourseNext->id)) {
            temp = temp->studentInCourseNext;
        }
        studentInCoursePOSITION new = (studentInCoursePOSITION) malloc(sizeof(struct studentInCourseNode));
        if (new != NULL) {
            strcpy(new->name, student->name);
            new->id = student->id;
            new->age = student->age;
            new->lenOfname = strlen(new->name);
            new->studentInCourseNext = temp->studentInCourseNext;
            temp->studentInCourseNext = new;
        }
    }
}
//this functions allows the user to add a course, after the header of the course lis,"time is taken into consideration"
void addCourse(courseLIST courseList){
    coursePOSITION temp=(coursePOSITION)malloc(sizeof(struct courseNode));
    char start[10];
    char finish[10];
    if(temp!=NULL){
        printf("Enter the name of the course,the id,the year,the start time,finish time,the maximum number of students\n");
        scanf(" %s %s %d %s %s %d",temp->c.name,temp->c.id,&temp->c.year,start,finish,&temp->c.maxNumOfStudent);
        temp->c.currentNum=0;
        temp->c.start=valueTime(start);
        temp->c.finish=valueTime(finish);
        temp->courseNext=courseList->courseNext;
        courseList->courseNext=temp;
        printf("DONE ADDING\n");
    }
}
//this function adds a student to students list, it is added after the header as well for saving time
void addStudent(studentLIST studentList){
    char course[10];
    studentPOSITION temp=(studentPOSITION)malloc(sizeof(struct studentNode));
    printf("Enter the name,id,age for the student\n");
    scanf("%s %d %d",temp->name,&temp->id,&temp->age);
    demandLIST l3=(demandLIST)malloc(sizeof(struct studentDemand));
    demandPOSITION temp3;
    temp->demandNEXT=l3;
    temp3=l3;
    printf("Enter the course id you would like the student to register for, please write (done) to stop adding courses\n");
    scanf("%s",course);
    while (strcmp(course,"done")!=0){
        demandPOSITION new3=(demandPOSITION)malloc(sizeof(struct studentDemand));
        strcpy(new3->c.name,course);
        temp3->demandNEXT=new3;
        temp3=new3;
        printf("Enter the next course, or done to stop adding courses\n");
        scanf("%s",course);
    }
    temp->studentNext=studentList->studentNext;
    studentList->studentNext=temp;
}
//radix sort function, descriped below:
void radixSortOfStudentsInCourses(courseLIST courseList,coursePOSITION course,char fileName[],int imp) {
    //Finding the name with the maximum letters//
    if (course->c.currentNum ==0) { //This is for printing all courses,if number of students is zero then  no need to continue//
        return;
    }
    else {
        int max = maxNumberOfLetters(course); //the number of iteration=number of letters in the course with max num of letters
        studentInCourseLIST headerForCopiedList = (studentInCourseLIST) malloc(sizeof(struct studentInCourseNode));
        if (headerForCopiedList != NULL) {//To check the malloc
            copyStudents(course->studentInCourseNext->studentInCourseNext, headerForCopiedList);//a copy of the students list in a course in needed
        }
        studentInCoursePOSITION copiedList = headerForCopiedList->studentInCourseNext;

        studentInCourseLIST A[27], B[27];//A,B are the like the pockets for the radix sort
        int i;
        //Getting the pockets ready//
        for (i = 0; i < 27; i++) {
            A[i] = (studentInCoursePOSITION) malloc(sizeof(struct studentInCourseNode));
            A[i]->studentInCourseNext = NULL;
            B[i] = (studentInCoursePOSITION) malloc(sizeof(struct studentInCourseNode));
            B[i]->studentInCourseNext = NULL;
        }
        studentInCoursePOSITION pos;
        studentInCoursePOSITION temp;
        int index;
        int currentNum = 1;
        //Filling list A from the students in the course//
        while (copiedList != NULL) {
            pos = returnStudentForRadixSort(headerForCopiedList);//the node to be sorted is extracted from the list and ready for use
            addSpace(pos, max);//spaces are added to the course name, util it has the same number of characters like the max course
            index = map(pos->name[max - 1]);//index is the index of Array A where the node should be added
            if (A[index]->studentInCourseNext == NULL) {
                A[index]->studentInCourseNext = pos;
            } else if (A[index]->studentInCourseNext != NULL) {
                temp = A[index];
                while (temp->studentInCourseNext != NULL) {
                    temp = temp->studentInCourseNext;
                }
                temp->studentInCourseNext = pos;
            }
            copiedList = headerForCopiedList->studentInCourseNext;
        }
        int j = 2;
        while (currentNum < max) {//going throug the iterations, a for loop may have a better use here!
            for (i = 0; i < 27; i++) {//going through all pockets
                pos = returnStudentForRadixSort(A[i]);
                while (pos != NULL) {
                    index = map(pos->name[max - j]);
                    if (B[index]->studentInCourseNext == NULL) {
                        B[index]->studentInCourseNext = pos;
                    } else if (B[index]->studentInCourseNext != NULL) {
                        temp = B[index];
                        while (temp->studentInCourseNext != NULL) {
                            temp = temp->studentInCourseNext;
                        }
                        temp->studentInCourseNext = pos;
                    }
                    pos = returnStudentForRadixSort(A[i]);
                }
            }
            j++;
            currentNum++;
            prepareForNewIteration(A, B);//moving the list form array A to B, to use array A in the new iteration
        }
        report(fileName, A, course, imp);//printing the sorting result in a file
        deletePocket(A);//free allocated memory
        deletePocket(B);//free allocated memory
    }
}
//addSpace function adds spaces to the course name,"using concatination", to ease the process of comapring the letters
void addSpace(studentInCoursePOSITION temp, int maxNumberOfLetters) {
    int i = 0;
    int j = strlen(temp->name);
    for (i = 0; i <= (maxNumberOfLetters - j); i++) {

        if (strlen(temp->name) == maxNumberOfLetters)
            break;
        else
            strcat(temp->name, " ");
    }
}
//this function accepts the course list and find the course with the maximum number of letter to know the number of iterations  needed in the radix sort
int maxNumberOfLetters(coursePOSITION course) {
    studentInCoursePOSITION temp;

    temp = course->studentInCourseNext->studentInCourseNext;
    int max = 0;
    while (temp != NULL) {
        if (strlen(temp->name) > max) {
            max = strlen(temp->name);
        }
        temp = temp->studentInCourseNext;
    }
    return max;
}
//this function is used to copy students list in a course, to be able to use the coppied list in the radix sort
void copyStudents(studentInCourseLIST l4, studentInCourseLIST headerForCopiedList) {
    studentInCoursePOSITION nodeToCopyFrom = l4;
    studentInCoursePOSITION temp, new;
    temp = headerForCopiedList;
    while (nodeToCopyFrom != NULL) {
        new = (studentInCoursePOSITION) malloc(sizeof(struct studentInCourseNode));
        strcpy(new->name, nodeToCopyFrom->name);
        new->id = nodeToCopyFrom->id;
        new->age = nodeToCopyFrom->age;
        new->lenOfname = nodeToCopyFrom->lenOfname;
        temp->studentInCourseNext = new;
        new->studentInCourseNext = NULL;
        temp = new;
        nodeToCopyFrom = nodeToCopyFrom->studentInCourseNext;
    }

}
//map funciton is used to give letters-and space- integer values to be used for comparing in radix sort
int map(char ch) {
    if (ch >= 97) {
        ch = ch - 32;
        return (ch - 65);

    } else if (ch >= 65)
        return (ch - 65);
    else {
        return 26;
    }
}
//this function returns the specific node to be used for sorting
studentInCoursePOSITION returnStudentForRadixSort(studentInCourseLIST l4){
    studentInCoursePOSITION temp=NULL;
    if(l4->studentInCourseNext!=NULL){
        temp=l4->studentInCourseNext;
        l4->studentInCourseNext=temp->studentInCourseNext;
        temp->studentInCourseNext=NULL;
    }
    return temp;
}
//this function moves the lists connected to array A, from A to B, to be able to use array A in the new iteration
void prepareForNewIteration(studentInCourseLIST A[], studentInCourseLIST B[]){
    int i;
    for (i=0;i<27;i++){
        A[i]->studentInCourseNext= B[i]->studentInCourseNext;
        B[i]->studentInCourseNext=NULL;
    }

}
//this function free the allocated memory for pocktes
void deletePocket(studentInCourseLIST l[]) {
    int i;
    studentInCoursePOSITION pos;
    for(i=0;i<27;i++){
        pos =l[i]->studentInCourseNext;
        while (pos!=NULL){
            l[i]->studentInCourseNext= pos->studentInCourseNext;
            free(pos);
            pos=l[i]->studentInCourseNext;
        }
    }
}
//this function prints the schedule of a student, described below
void scheduleOfAStudent(studentLIST studentList,int id) {
    //Finding the student//
    int i;
    char fileName[20];
    printf("Enter the name of the file you would like to have the scedule on:\n");//asking the user for the file name to print on
    scanf("%s",fileName);
    FILE *out=fopen(fileName,"w");
    studentPOSITION p = studentList->studentNext;
    while ((p != NULL) && (p->id != id)) {//searchhing for the student
        p = p->studentNext;
    }
    if (p == NULL) {
        fprintf(out,"NO SUCH STUDENT with an id: %d\n",id);
    }
    else {
        char day[7][15] = {"Saturday  ", "Sunday    ", "Monday    ", "Tuesday   ", "Wednesday ", "Thursday  ",
                           "Friday    "};
        if (p->currentNumOfCourse == 0) {//if student has no courses//
            fprintf(out,"This student is not registered in any course based on the condition\n");
            fprintf(out, "Student Name: %s\t\t\tStudent ID: %d\n", p->name, p->id);
            fprintf(out,
                    "*********************************************************************************************************************************************|\n");
            fprintf(out,
                    "|Day       |8           |9           |10          |11          |12          |13          |14          |15          |16          |17          |\n");
            fprintf(out,
                    "|**********|************|************|************|************|************|************|************|************|************|************|\n");
            for (i = 0; i < 7; i++) {
                fprintf(out, "|%s|", day[i]);
                if (i == 1 || i == 6) {
                    fprintf(out, "\n");
                    continue;
                }
                fprintf(out,"\n");
            }
            fprintf(out,
                    "|**********|************|************|************|************|************|************|************|************|************|************|\n");

        }
        else{//if student has courses easily//
            i;
            int j;
            int time, spaces, durationSpace;
            fprintf(out, "Student Name: %s\t\t\tStudent ID: %d\n", p->name, p->id);
            fprintf(out,"************************************************************************************************************************************************\n");
            demandLIST courses[p->currentNumOfCourse];//an array of demandpositions will be created
            copyingForSchedule(courses, p);//the courses array is now ready for use
            sortCoursesForSchedule(courses, p->currentNumOfCourse);//the courses are sorted based on starting time of course
            fprintf(out,
                    "|Day       |8           |9           |10          |11          |12          |13          |14          |15          |16          |17          |\n");
            fprintf(out,
                    "|**********|************|************|************|************|************|************|************|************|************|************|\n");
            //10 minutes are counted as a space//
            for (i = 0; i < 7; i++) {
                fprintf(out, "|%s|", day[i]);
                if (i == 1 || i == 6) {
                    fprintf(out, "\n");
                    fprintf(out,
                            "|**********|************|************|************|************|************|************|************|************|************|************|\n");
                    continue;
                }

                time = (courses[0]->c.start) -
                       800;//According to the numbering method I am used to handle the 24-hour format//
                spaces = ((time % 100) / 10 * 2) + ((time / 100) *12);//the first part to count the spaces for minutes, the second part for hours,where each hour is a 12 space//
                if (spaces % 12 != 0)
                    spaces++;//to have the perfect scheduale,if the spaces are not a multiple of 12, then a space would be missed for"|"//
                spaces += (spaces / 12);//need to add more spaces for "|" mark//
                time = (courses[0]->c.finish - courses[0]->c.start);
                durationSpace = ((time % 100) / 10 * 2) + ((time / 100) * 12);
                durationSpace -= strlen(courses[0]->c.id);
                fprintf(out, "%*s%s%*s", spaces, "|", courses[0]->c.id, durationSpace + 1,
                        "|");//the one was added at durationSpace because we need one for the "|" mark//
                for (j = 1; j < p->currentNumOfCourse; j++) {
                    if ((courses[j]->c.start % 100 == 0))
                        time = courses[j]->c.start - (courses[j - 1]->c.finish) - 40;
                    else
                        time = (courses[j]->c.start - (courses[j - 1]->c.finish));
                    if (time >= 50 && time < 100)
                        time -= 40;
                    spaces = ((time % 100) / 10 * 2) + ((time / 100) * 12);
                    if (spaces % 12 != 0)
                        spaces++;
                    spaces += (spaces / 12);
                    time = (courses[j]->c.finish - courses[j]->c.start);
                    if (time >= 50 && time < 100)
                        time -= 40;
                    durationSpace = ((time % 100) / 10 * 2) + ((time / 100) * 12);
                    durationSpace -= (strlen(courses[j]->c.id));
                    fprintf(out, "%*s%s%*s", spaces - 1, "|", courses[j]->c.id, durationSpace + 1, "|");
                }
                fprintf(out,
                        "\n|**********|************|************|************|************|************|************|************|************|************|************|\n");
            }
        }
    }
    fclose(out);
}
//this function copies the courses a student is registered in into the courses array used for the schedule
void copyingForSchedule(demandLIST courses[],studentPOSITION p){
    demandPOSITION temp=p->demandNEXT->demandNEXT;
    int i=0;
    while (temp!=NULL) {
        courses[i] = (demandLIST) malloc(sizeof(struct studentDemand));
        if (temp->flag != 0) {
            strcpy(courses[i]->c.name, temp->c.name);
            strcpy(courses[i]->c.id, temp->c.id);
            courses[i]->c.start = temp->c.start;
            courses[i]->c.finish = temp->c.finish;
            i++;
        }
        temp = temp->demandNEXT;
    }
}
//this function sorts the courses in the course list used in printing the schedule based on courses start times
void sortCoursesForSchedule(demandLIST courses[],int n){
    demandPOSITION temp;
    int i,j;
    for (i = 0; i<n ; i++) {
        for(j=i+1; j<n;j++){
            if(courses[i]->c.start>courses[j]->c.start){
                temp=courses[i];
                courses[i]=courses[j];
                courses[j]=temp;
            }
        }

    }
}
//when a course is read from a file, it is added in ascending order according to the registration year
void sortCourse(courseLIST courseList,coursePOSITION p){
    coursePOSITION temp=courseList;
    if(temp->courseNext==NULL){
        temp->courseNext=p;
        p->courseNext=NULL;
    }
    else{
        while((temp->courseNext!=NULL)&&(p->c.year>temp->courseNext->c.year)){
            temp=temp->courseNext;
        }
        p->courseNext = temp->courseNext;
        temp->courseNext = p;
    }
}
//A search function that returns a coursePOSITION
coursePOSITION findCourse(courseLIST courseList,char courseId[]){
    coursePOSITION temp=courseList->courseNext;
    while ((temp!=NULL)&&(strcmp(temp->c.id,courseId)!=0)){
        temp=temp->courseNext;
    }
    return temp;
}
//this function frees the allocated memory for courses linked list
void deletCourseList(courseLIST l){
    coursePOSITION p;
    while(l->courseNext!=NULL) {
        p = l->courseNext;
        l->courseNext = p->courseNext;
        free(p);
    }
}
//this function frees the allocated memory for students linked list
void deletStudentList(studentLIST l){
    studentPOSITION p;
    while (l->studentNext!=NULL){
        p=l->studentNext;
        l->studentNext=p->studentNext;
        free(p);
    }
}
//preparingForReport1 function, asks the user for the required course to print its students sorted, and for a file name, and prints in the required first format
void preparingForReport1(courseLIST courseList){
    char courseId[20];
    char fileName[20];
    printf("Enter the course ID (e.g COMP242),to get its students sorted by name\n");
    scanf("%s",courseId);
    coursePOSITION course=findCourse(courseList,courseId);
    if(course!=NULL) {
        printf("Enter file name to print the sorted students in the entered course in\n");
        scanf("%s",fileName);
        radixSortOfStudentsInCourses(courseList, course,fileName,0);
        printf("The Report is READY!\n");
    }
    else
        printf("No Such Course!");
}
//this function formats the required third report, it asks the user for an output file and prints there
void preparingForReport2(courseLIST courseList){
    char fileName[20];
    printf("Enter file name to print the sorted students in the entered course in\n");
    scanf("%s",fileName);
    coursePOSITION temp=courseList->courseNext;
    radixSortOfStudentsInCourses(courseList, temp ,fileName,0);
    temp=temp->courseNext;
    while(temp!=NULL){
        radixSortOfStudentsInCourses(courseList, temp ,fileName,1);
        temp=temp->courseNext;
    }
    printf("The Report is READY!\n");
}
//this functions print the result of radix sort, it accepts an "imp" to check whether the file is to be opened and writen on OR opened to append more data on
void report(char fileName[],studentInCourseLIST A[],coursePOSITION course,int imp){
    //printing the report in the specific file//
    FILE* out;
    if(imp==0)
        out=fopen(fileName,"w");
    else if(imp==1)
        out=fopen(fileName,"a");

    fprintf(out,"***************************************************************************************************************\n");
    fprintf(out,"Course Name: %s\tCourse ID: %s\n",course->c.name,course->c.id);
    fprintf(out, "***************************************************************************************************************\n");
    fprintf(out,"THE STUDENTS IN THIS COURSE ARE:\n");
    fprintf(out,"***************************************************************************************************************\n");
    int j=0,i;
    for (i = 0; i < 27; i++) {
        studentInCoursePOSITION temp = A[i]->studentInCourseNext;
        while (temp != NULL) {
            fprintf(out,"Student %d:%15s %7d %3d\n",(j+1),temp->name,temp->id,temp->age);
            fprintf(out,"***************************************************************************************************************\n");
            temp = temp->studentInCourseNext;
            j++;
        }
    }
    fclose(out);
}
//this function is called to prepare for printing the fourth required report
void preparingForReport4(courseLIST l){
    char fileName[20];
    printf("Enter file name you would like to have the courses in\n");
    scanf("%s",fileName);
    FILE* out=fopen(fileName,"w");
    fprintf(out,"A course list that contains, the course name, id and number of registered students in that\n"
                "course (ordered based on the year):\n");
    coursePOSITION temp=l->courseNext;
    while (temp!=NULL){
        fprintf(out,"course name: %s,course id: %s, number of registered students: %d\n",temp->c.name,temp->c.id,temp->c.currentNum);
        temp=temp->courseNext;
    }
    fclose(out);
    printf("Report is READY!\n");
}
//this function formats report three as required
void preparingForReport3(studentLIST l){
    int id;
    printf("Enter the id of the student you would like to see his\her schedule:\n");
    scanf("%d",&id);
    scheduleOfAStudent(l,id);
    printf("Report is READY!\n");
}
//menu function that is called to give the users the options available
void menu() {
    printf("\t\t\t\t\t\t\t\tWelcome to The Registration System\n");
    printf("1-Report the student's list in a chosen course ordered based on student's name\n");
    printf("2-Report the students in all courses ordered based on student's name\n");
    printf("3-Report the schedule of a student after entering his\her ID\n");
    printf("4-Repot all courses ordered based on registration year\n");
    printf("5-Add student to the students list\n");
    printf("6-End your journey\n");
    printf("-------------------------------\n");
    printf("Please make a choice: \n");
}
