#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>

#define MAX_STUDYING_TIME   10
#define MAX_QUEUE_LENGTH    3
#define TA_SLEEPING_PROBABILLITY    2

int nStudent = 0;
int nStudentInQueue = 0;
pthread_mutex_t QueueMutex;
pthread_mutex_t TARoomMutex;
pthread_cond_t  TASleepingCond;

typedef struct nodeStudent {
    struct nodeStudent *next;
    int ID;
    sem_t sem;
} nodeStudent;

nodeStudent *head = NULL, *tail = NULL;
nodeStudent *StudentBeHelped = NULL;

int appendToQueue(nodeStudent *Student) {
    int nRet = 0;
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
        nRet = 0;
    } else {
        nRet = -1;
    }
    pthread_mutex_unlock(&QueueMutex);

    return nRet;
}

int getFromQueue(nodeStudent **Student) {
    int nRet = 0;

    pthread_mutex_lock(&QueueMutex);
    if (nStudentInQueue > 0) {
        *Student = head;
        head = head->next;

        nStudentInQueue--;
        nRet = 0;
    } else {
        nRet = -1;
    }
    pthread_mutex_unlock(&QueueMutex);

    return nRet;
}

void s_SleepTA()
{
    pthread_mutex_lock(&TARoomMutex);
    while (StudentBeHelped == NULL) {
        printf("TA fucking sleeeeeeeeeeep\n");
        pthread_cond_wait(&TASleepingCond, &TARoomMutex);
    }
    pthread_mutex_unlock(&TARoomMutex);
}

int s_CheckTA(nodeStudent *KnockingStudent)
{
    int nRet = 0;

    pthread_mutex_lock(&TARoomMutex);
    if (StudentBeHelped == NULL) {
        StudentBeHelped = KnockingStudent;
        printf("Student %d fucking wakeup TAAAAAA\n", KnockingStudent->ID);
        pthread_cond_signal(&TASleepingCond);
        nRet = 1;
    }
    pthread_mutex_unlock(&TARoomMutex);

    return nRet;
}

void* CreateStudent(void* pParam) {
    nodeStudent *Student = NULL;
    int nStudyingTime = 0;

    Student = (nodeStudent *)malloc(sizeof(nodeStudent));
    Student->ID = (int)pParam;
    printf("Create student %d\n", Student->ID);
    Student->next = NULL;
    sem_init(&(Student->sem), 0, 0);

    //studying and trying to ask question to TA
    while (1) {
        nStudyingTime = rand() % MAX_STUDYING_TIME + 1;
        printf("student %d study for %d second\n", Student->ID, nStudyingTime);
        sleep(nStudyingTime);

        printf("student %d try to ask TA for help\n", Student->ID);
        if (s_CheckTA(Student) == 1) {
            printf("student %d get into TARoom\n", Student->ID);
            sem_wait(&(Student->sem));
            break;
        } else if (appendToQueue(Student) == 0) {
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
    return NULL;
}

void* CreateTA(void* pParam) {

    while (1) {
        if (StudentBeHelped == NULL) {
            getFromQueue(&StudentBeHelped);
        }
        if (StudentBeHelped != NULL) {
            int helping_time = rand() % 5;

            printf("TA is helping student %d for %d sec\n", StudentBeHelped->ID, helping_time);
            sleep(helping_time);
            sem_post(&(StudentBeHelped->sem));
            StudentBeHelped = NULL;
        } else {
            if (rand() % TA_SLEEPING_PROBABILLITY == 1) {
                //TA take a nap
                s_SleepTA();
            }
        }
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
    fscanf(stdin, "%d", &nStudent);

    srand(time(NULL));
    pthread_mutex_init(&QueueMutex, NULL);
    pthread_mutex_init(&TARoomMutex, NULL);
    pthread_cond_init(&TASleepingCond, NULL);

    pid = (pthread_t *)calloc(nStudent, sizeof(pthread_t));
    
    for (i = 0; i < nStudent; i++) {
        pthread_create(&pid[i], NULL, CreateStudent, (void *)i);
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