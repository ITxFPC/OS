package main

import (
	"fmt"
	"math"
)

func main() {
	var start,end,year float64
	fmt.Printf("enter start : ")
	_, _ = fmt.Scanf("%f", &start)

	fmt.Printf("enter end : ")
	_, _ = fmt.Scanf("%f", &end)

	fmt.Printf("enter year : ")
	_, _ = fmt.Scanf("%f", &year)

	result := math.Pow(end/start, 1/year)-1

	fmt.Println(result)
}