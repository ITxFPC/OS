package main

import (
	"fmt"
	"sync"
	"time"
)

func withdraw1() {
	balance := money1
	time.Sleep(3000 * time.Millisecond)
	balance -= 1000
	money1 = balance
	fmt.Println("After withdrawing $1000, balace: ", money)
	wg1.Done()
}

var wg1 sync.WaitGroup
var money1 int = 1500

func main() {
	fmt.Println("We have $1500")
	wg1.Add(2)
	go withdraw1() // first withdraw
	go withdraw1() // second withdraw
	wg1.Wait()
}
