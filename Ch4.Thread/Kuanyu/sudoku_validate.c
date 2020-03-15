#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define _GNU_SOURCE
#include <pthread.h>
#include <signal.h>
#include <sys/time.h>
#include <ctype.h>

int nBlockToCheck = 0;
pthread_mutex_t BlockToCheckLock;
int SudokuArray[9 * 9] = {0};
int bValidSudoku = 1;
pthread_t pid[3] = {0};

static int s_GetBlockIndexToCheck()
{
    int nRet = 0;
    pthread_mutex_lock(&BlockToCheckLock);

    if (nBlockToCheck < 8) {
        nRet = nBlockToCheck;
        nBlockToCheck++;
    }

    pthread_mutex_unlock(&BlockToCheckLock);

    return nRet;
}

static void s_KillAllChildThread()
{
    int i = 0;

    for (i = 0; i < 3; i++) {
        if (pid[i] > 0) {
            pthread_kill(pid[i], SIGKILL);
            pid[i] = 0;
        }
    }
}

static void* s_CheckRow(void* pvParam)
{
}

static void* s_CheckColum(void* pvParam)
{
}

static void* s_CheckBlock(void* pvParam)
{
}

static int s_ValidateSudokuWithThread()
{
    int i = 0;
    int nThreadRet = 0;

    pthread_create(&pid[0], NULL, s_CheckRow, NULL);
    pthread_create(&pid[1], NULL, s_CheckColum, NULL);
    pthread_create(&pid[2], NULL, s_CheckBlock, NULL);

    for (i = 0; i < 3; i = (i + 1) % 3) {
        if (pthread_tryjoin_np(pid[i], NULL) == 0) {
            if (!bValidSudoku) {
                s_KillAllChildThread();
                printf("Sudoku Invalid!\n");
                break;
            }
        }
    }

    return 0;
}

static int s_ValidateSudoky()
{
    int nRet = 0;

    s_CheckRow(NULL);
    if (!bValidSudoku) {
        nRet = -1;
        goto end;
    }

    s_CheckColum(NULL);
    if (!bValidSudoku) {
        nRet = -1;
        goto end;
    }

    s_CheckBlock(NULL);
    if (!bValidSudoku) {
        nRet = -1;
        goto end;
    }

end:
    if (nRet != 0) {
        printf("Sudoku invalid! single thread\n");
    }
    return 0;
}

int 
main(int argc, char *argv[])
{
    int i = 0;
    char cTmp = '\0';
    char *pcFilename = argv[1];
    FILE *fp = NULL;
    struct timeval tStart, tEnd;

    fp = fopen(pcFilename, "r");
    if (fp == NULL) {
        goto end;
    }

    while ((cTmp = fgetc(fp)) != EOF) {
        if (isdigit(cTmp)) {
           SudokuArray[i++] = cTmp - '0';
        }
    }

    for (i = 0;i < 9 * 9; i++) {
        printf("%d ", SudokuArray[i]);
        if (i % 9 == 8) {
            printf("\n");
        }
    }

    if (pthread_mutex_init(&BlockToCheckLock, NULL) != 0) {
        goto end;
    }

//    gettimeofday(&tStart, NULL);
//    gettimeofday(&tEnd, NULL);
//    printf("spend time: %ld ms\n", ((1000000 * (tEnd.tv_sec - tStart.tv_sec) + tEnd.tv_usec - tStart.tv_usec) / 1000));

end:
    if (fp != NULL) {
        fclose(fp);
        fp = NULL;
    }
    pthread_mutex_destroy(&BlockToCheckLock);
}