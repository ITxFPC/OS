#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

int *pnFibSeq = NULL;
int nFibNum = 0;

static int
s_GetFibSeq(int n)
{
    int nRet = 0;

    if (n > nFibNum || pnFibSeq == NULL) {
        return -1;
    }
    if (n == 0) {
        nRet = 0;
    } else if (n == 1) {
        nRet = 1;
    } else {
        if (pnFibSeq[n] != 0) {
            return pnFibSeq[n];
        } else {
            nRet = s_GetFibSeq(n - 1) + s_GetFibSeq(n - 2);
        }
    }

    pnFibSeq[n] = nRet;

    return nRet;
}

static void*
s_CallFib(void *pParam)
{
    s_GetFibSeq(*(int*)(pParam));

    // pthread_exit(0);
    return NULL;
}

int
main()
{
    int i = 0;
    int nArraySize = 0;
    pthread_t tid = -1;

    scanf("%d", &nFibNum);
    nArraySize = nFibNum + 1;

    pnFibSeq = (int *)calloc(nArraySize, sizeof(int));
    if (pnFibSeq == NULL) {
        printf("malloc failed");
        return -1;
    }

    pthread_create(&tid, NULL, s_CallFib, (void *)&nFibNum);
    pthread_join(tid, NULL);

    for (i == 0; i < nFibNum; i++) {
        printf("%d ,", pnFibSeq[i]);
    }
    printf("%d", pnFibSeq[i]);

    if (pnFibSeq != NULL) {
        free(pnFibSeq);
        pnFibSeq = NULL;
    }
}