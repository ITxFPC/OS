#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>

int nBlockToCheck = 0;
pthread_mutex_t BlockToCheckLock;
int SudokuArray[9 * 9] = {0};

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

static int s_CheckRow()
{
}

static int s_CheckColum()
{
}

static int s_CheckBlock()
{}

int 
main(int argc, char *argv[])
{
    int nIntNum = 0;
    int *nzIntSeq = NULL;
    int i = 0, nTmp = 0;
    char *pcFilename = argv[1];
    FILE *fp = NULL, *outputfp = NULL;
    MergeSortParam stParam;
    struct timeval tStart, tEnd;

    fp = fopen(pcFilename, "r");
    if (fp == NULL) {
        goto end;
    }

    while (fscanf(fp, "%d", &nTmp) != EOF) {
        i++;
    }
    nIntNum = i;

    nzIntSeq = (int *)calloc(nIntNum, sizeof(int));
    if (nzIntSeq == NULL) {
        goto end;
    }
    rewind(fp);

    i = 0;
    while (fscanf(fp, "%d", &nzIntSeq[i++]) != EOF);

    stParam.pnArray = nzIntSeq;
    stParam.nBegin = 0;
    stParam.nEnd = nIntNum - 1;

    if (pthread_mutex_init(&BlockToCheckLock, NULL) != 0) {
        goto end;
    }

    gettimeofday(&tStart, NULL);
    s_DoMergeSort((void *)&stParam);
    gettimeofday(&tEnd, NULL);
    printf("spend time: %ld ms\n", ((1000000 * (tEnd.tv_sec - tStart.tv_sec) + tEnd.tv_usec - tStart.tv_usec) / 1000));

    outputfp = fopen("output", "w");
    for (i = 0; i < nIntNum; i++) {
        fprintf(outputfp, "%d\n", nzIntSeq[i]);
    }
end:
    if (fp != NULL) {
        fclose(fp);
        fp = NULL;
    }
    if (outputfp != NULL) {
        fclose(outputfp);
        outputfp = NULL;
    }
    if (nzIntSeq != NULL) {
        free(nzIntSeq);
        nzIntSeq = NULL;
    }
    pthread_mutex_destroy(&BlockToCheckLock);
}