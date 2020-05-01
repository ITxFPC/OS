package main

import (
	"encoding/csv"
	"fmt"
	"github.com/sirupsen/logrus"
	"os"
	"strconv"
)

func main() {
	available, err := readCsv("test-data/max.csv")
	if err != nil{
		logrus.Error(err)
	}
	fmt.Println(available)
}

func readCsv (filename string) ([][]uint64, error)  {
	f, err := os.Open(filename)
	if err != nil {
		return nil, err
	}
	defer f.Close()

	lines, err := csv.NewReader(f).ReadAll()
	if err != nil {
		return nil, err
	}
	result := make([][]uint64,0,100)
	for i, line := range lines {
		for j, l := range line {
			valUint, err := strconv.ParseUint(l,10,64)
			if err != nil {
				return nil, err
			}
			fmt.Println(valUint,i,j)
			result[i][j]= valUint
		}
	}
	return result, nil
}