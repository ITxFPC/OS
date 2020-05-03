package main

import (
	"encoding/csv"
	"fmt"
	"github.com/sirupsen/logrus"
	"os"
	"strconv"
)

func main() {
	max, err := readCsv("test-data/max.csv")
	if err != nil{
		logrus.Error(err)
	}
	fmt.Println(max)
}

func readCsv (filename string) ([][]uint64, error)  {
	f, err := os.Open(filename)
	if err != nil {
		return nil, err
	}
	defer f.Close()

	lines, cErr := csv.NewReader(f).ReadAll()
	if cErr != nil {
		return nil, cErr
	}
	result := make([][]uint64, len(lines))
	for i := 0; i < len(lines); i++ {
		result[i] = make([]uint64, len(lines))
	}
	for i, line := range lines {
		for j, l := range line {
			valUint, err := strconv.ParseUint(l,10,64)
			if err != nil {
				return nil, err
			}
			result[i][j] = valUint
		}
	}
	return result, nil
}