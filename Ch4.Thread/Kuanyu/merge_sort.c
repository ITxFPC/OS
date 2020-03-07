#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <pthread.h>

typedef struct MergeSortParam {
    int *pnArray;
    int nBegin;
    int nEnd;
} MergeSortParam;

int nThreadNum = 0;
pthread_mutex_t ThreadNumLock;

static int s_AllowCallThread()
{
    int nRet = 0;
    pthread_mutex_lock(&ThreadNumLock);

    if (nThreadNum > 0) {
        nRet = 1;
        nThreadNum--;
    }

    pthread_mutex_unlock(&ThreadNumLock);

    return nRet;
}

static int
s_MergeSortedArray(int *pnzSortingArray, int nBegin, int nMid, int nEnd)
{
    int *nzTmpArray = NULL;
    int i = 0, l = 0, r = 0;

    nzTmpArray = (int *)calloc(nEnd - nBegin + 1, sizeof(int));

    for (l = nBegin, r = nMid + 1, i = 0; l <= nMid && r <= nEnd;) {
        nzTmpArray[i] = pnzSortingArray[l] < pnzSortingArray[r] ? pnzSortingArray[l] : pnzSortingArray[r];
    }
    if (l <= nMid) memcpy(&nzTmpArray[i], &pnzSortingArray[l], sizeof(int) * (nMid - l + 1));
    else if (r <= nEnd) memcpy(&nzTmpArray[i], &pnzSortingArray[r], sizeof(int) * (nEnd - r + 1));

    return 0;
}

static void
s_DoMergeSort(void *pParam)
{
    MergeSortParam  *pstParam = (MergeSortParam *)pParam;
    MergeSortParam  stParamToPassLeft;
    MergeSortParam  stParamToPassRight;
    int nMid = (pstParam->nEnd + pstParam->nBegin) / 2;
    pthread_t   pid[2] = {0};
    if (pstParam->nEnd == pstParam->nBegin) {
        return 0;
    }
    stParamToPassLeft.pnArray = pstParam->pnArray;
    stParamToPassLeft.nBegin = pstParam->nBegin;
    stParamToPassLeft.nEnd = nMid;
    if (s_AllowCallThread()) {
        pthread_create(&pid[0], NULL, s_DoMergeSort, (void *)stParamToPassLeft);
    } else {
        s_DoMergeSort((void *)&stParamToPassLeft);
    }
    stParamToPassRight.pnArray = pstParam->pnArray;
    stParamToPassRight.nBegin = nMid + 1;
    stParamToPassRight.nEnd = pstParam->nEnd;
    if (s_AllowCallThread()) {
        pthread_create(&pid[1], NULL, s_DoMergeSort, (void *)stParamToPassRight);
    } else {
        s_DoMergeSort((void *)&stParamToPassRight);
    }
    if (pid[0] > 0) {
        pthread_join(pid[0], NULL);
    }
    if (pid[1] > 0) {
        pthread_join(pid[1], NULL);
    }

    s_MergeSortedArray(pstParam->pnArray, pstParam->nBegin, nMid, pstParam->nEnd);
}

int 
main(int argc, char *argv[])
{
    int nIntNum = 0;
    int *nzIntSeq = NULL;
    int i = 0, nTmp = 0;
    char *pcFilename = argv[1];
    FILE *fp = NULL;
    MergeSortParam stParam;

    nThreadNum = atoi(argv[2]);
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
    stParam.nEnd = nIntNum;

    if (pthread_mutex_init(&ThreadNumLock, NULL) != 0) {
        goto end;
    }

    s_DoMergeSort((void *)&stParam);
end:
    if (fp != NULL) {
        fclose(fp);
        fp = NULL;
    }
    if (nzIntSeq != NULL) {
        free(nzIntSeq);
        nzIntSeq = NULL;
    }
    pthread_mutex_destroy(&ThreadNumLock);
}