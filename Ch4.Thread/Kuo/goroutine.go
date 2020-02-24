package main

import (
    "fmt"
    "sync"
    "time"
)

func withdraw() {
    // 當使用mu.Lock()的時候，之後所用到的變數就會上鎖，只有在使用中的Thread可以存取，其他都需要等到釋放後才能存取
    mu.Lock()
    balance := money
    time.Sleep(3000 * time.Millisecond)
    balance -= 1000
    money = balance
    // 釋放lock的變數
    mu.Unlock()
    fmt.Println("After withdrawing $1000, balance: ", money)
    wg.Done()
}

var wg sync.WaitGroup
var money = 1500
var mu sync.Mutex

/*
func main() {
    fmt.Println("We have $1500")
    wg.Add(2)
    go withdraw() // first withdraw
    go withdraw() // second withdraw
    wg.Wait()
}
 */
