#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>

#define MAX_STUDYING_TIME   2
#define MAX_QUEUE_LENGTH    3

int nStudent = 0;
int nStudentInQueue = 0;
pthread_mutex_t QueueMutex;

typedef struct nodeStudent {
    struct nodeStudent *next;
    pthread_t ID;
    sem_t sem;
} nodeStudent;

nodeStudent *head = NULL, *tail = NULL;

int appendToQueue(nodeStudent *Student) {
    pthread_mutex_lock(&QueueMutex);
    if (nStudentInQueue < MAX_QUEUE_LENGTH) {
        if (head == NULL) {
            head = Student;
            tail = Student;
        } else {
            tail->next = Student;
            tail = tail->next;
        }
        nStudentInQueue++;
        return 0;
    } else {
        return -1;
    }
    pthread_mutex_unlock(&QueueMutex);
}

int getFromQueue(nodeStudent **Student) {
    pthread_mutex_lock(&QueueMutex);
    if (nStudentInQueue > 0) {
        *Student = head;
        head = head->next;

        nStudentInQueue--;
        return 0;
    } else {
        return -1;
    }
    pthread_mutex_unlock(&QueueMutex);
}

void* CreateStudent(void* pParam) {
    nodeStudent *Student = NULL;
    int nStudyingTime = 0;

    Student = (nodeStudent *)malloc(sizeof(nodeStudent));
    Student->ID = pthread_self();
    Student->next = NULL;
    sem_init(&(Student->sem), 0, 0);

    srand(time(NULL));
    //studying and trying to ask question to TA
    while (1) {
        nStudyingTime = rand() % MAX_STUDYING_TIME + 1;
        printf("student %d study for %d second\n", Student->ID, nStudyingTime);
        sleep(nStudyingTime);

        printf("student %d try to ask TA for help\n", Student->ID);
        if (appendToQueue(Student) == 0) {
            printf("student %d get into queue\n", Student->ID);
            sem_wait(&(Student->sem));
            break;
        }        
        printf("Queue is full, student %d go back to study\n", Student->ID);
    }

    if (Student != NULL) {
        sem_destroy(&(Student->sem));
        free(Student);
        Student = NULL;
    } 
}

void* CreateTA(void* pParam) {
    int i = 0;
    nodeStudent *StudentBeHelped = NULL;

    for (i = 0; i < nStudent; i++) {
        sleep(3);
        getFromQueue(&StudentBeHelped);
        printf("TA is helping student %d\n", StudentBeHelped->ID);
        sem_post(&(StudentBeHelped->sem));
        StudentBeHelped = NULL;
    }
    
    return NULL;
}

int
main()
{
    int i = 0;
    pthread_t *pid = NULL;
    pthread_t TAid = -1;

    printf("Please enter the number of student: ");
    fscanf("%d", &nStudent);

    pthread_mutex_init(&QueueMutex, NULL);
    pid = (pthread_t *)calloc(nStudent, sizeof(pthread_t));
    for (i = 0; i < nStudent; i++) {
        pthread_create(&pid[i], NULL, CreateStudent, NULL);
    }
    pthread_create(&TAid, NULL, CreateTA, NULL);

    for (i = 0; i < nStudent; i++) {
        pthread_join(pid[i], NULL);
    }
    
    if (pid != NULL) {
        free(pid);
    }
    return 0;
}