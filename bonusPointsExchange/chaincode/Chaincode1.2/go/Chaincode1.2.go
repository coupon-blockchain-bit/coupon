// Chaincode for hyperledger fabric v1.2, written by yulong
package main

import (
	"fmt"
	"strconv"

	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)

// CORE_PEER_ADDRESS=peer:7052 CORE_CHAINCODE_ID_NAME=mycc:0 ./Chaincode1.2

// peer chaincode install -p chaincodedev/chaincode/Chaincode1.2 -n mycc -v 0
// peer chaincode instantiate -n mycc -v 0 -c '{"Args":["Init","a","100","b","100"]}' -C myc
// peer chaincode invoke -n mycc -c '{"Args":["invoke", "a", "b","20","20"]}' -C myc
// peer chaincode invoke -n mycc -c '{"Args":["query", "a"]}' -C myc
// peer chaincode invoke -n mycc -c '{"Args":["query", "b"]}' -C myc
// peer chaincode invoke -n mycc -c '{"Args":["addAcc", "c","200"]}' -C myc
// peer chaincode invoke -n mycc -c '{"Args":["query", "c"]}' -C myc
// peer chaincode invoke -n mycc -c '{"Args":["transfer", "c","0","50"]}' -C myc
// peer chaincode invoke -n mycc -c '{"Args":["delete", "c"]}' -C myc
// SimpleChaincode example simple Chaincode implementation
type SimpleChaincode struct {
}

func (t *SimpleChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response {

	_, args := stub.GetFunctionAndParameters()
	var A, B string    // Entities
	var Aval, Bval int // Asset holdings
	var err error

	if len(args) != 4 {
		return shim.Error("Incorrect number of arguments. Expecting 4")
	}

	// Initialize the chaincode
	A = args[0]
	Aval, err = strconv.Atoi(args[1])
	if err != nil {
		return shim.Error("Expecting integer value for asset holding")
	}
	B = args[2]
	Bval, err = strconv.Atoi(args[3])
	if err != nil {
		return shim.Error("Expecting integer value for asset holding")
	}
	fmt.Printf("Aval = %d, Bval = %d\n", Aval, Bval)

	// Write the state to the ledger
	err = stub.PutState(A, []byte(strconv.Itoa(Aval)))
	if err != nil {
		return shim.Error(err.Error())
	}

	err = stub.PutState(B, []byte(strconv.Itoa(Bval)))
	if err != nil {
		return shim.Error(err.Error())
	}

	return shim.Success(nil)
}

func (t *SimpleChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	fmt.Println("ex02 Invoke")
	function, args := stub.GetFunctionAndParameters()
	if function == "invoke" {
		// Make payment of X units from A to B
		return t.invoke(stub, args)
	} else if function == "delete" {
		// Deletes an entity from its state
		return t.delete(stub, args)
	} else if function == "query" {
		// the old "Query" is now implemtned in invoke
		return t.query(stub, args)
	} else if function == "addAcc" {
		return t.addAcc(stub, args)
	} else if function == "transfer" {
		return t.transfer(stub, args)
	}

	return shim.Error("Invalid invoke function name. Expecting \"invoke\" \"delete\" \"query\"")
}

//args :Entities,Asset
func (t *SimpleChaincode) addAcc(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var N string // Entities
	var Nval int // Asset holdings
	var err error

	if len(args) != 2 {
		return shim.Error("")
	}

	N = args[0]
	Nval, err = strconv.Atoi(args[1])
	if err != nil {
		return shim.Error("Expecting integer value for asset holding")
	}

	err = stub.PutState(N, []byte(strconv.Itoa(Nval)))
	if err != nil {
		return shim.Error("Putstate failed")
	}
	return shim.Success([]byte("ok"))

}

func (t *SimpleChaincode) transfer(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var M string
	var flag, Mval, num int
	var err error

	if len(args) != 3 {
		return shim.Error("Incorrect number of arguments. Expecting 3")
	}

	//M is account
	M = args[0]
	Mvalbytes, err := stub.GetState(M)
	if err != nil {
		return shim.Error("Failed to get state")
	}
	if Mvalbytes == nil {
		return shim.Error("Entity not found")
	}
	Mval, _ = strconv.Atoi(string(Mvalbytes))

	//flag defines minus/add(0/1)
	flag, err = strconv.Atoi(args[1])
	if err != nil {
		return shim.Error("Expecting integer value for flag holding")
	}

	//num is changes of money
	num, err = strconv.Atoi(args[2])
	if err != nil {
		return shim.Error("Expecting integer value for modify holding")
	}

	//if minus
	if flag == 0 {
		Mval = Mval - num
		err = stub.PutState(M, []byte(strconv.Itoa(Mval)))
		if err != nil {
			return shim.Error("Failed to get state")
		}
		return shim.Success(nil)
	}

	//if add
	if flag == 1 {
		Mval = Mval + num
		err = stub.PutState(M, []byte(strconv.Itoa(Mval)))
		if err != nil {
			return shim.Error("Failed to get state")
		}
		return shim.Success(nil)
	}
	return shim.Success([]byte("ok"))
}

// Transaction makes payment of X units from A to B
//args:entity entity value value
func (t *SimpleChaincode) invoke(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var A, B string    // Entities
	var Aval, Bval int // Asset holdings
	var X, Y int       // Transaction value
	var err error

	if len(args) != 4 {
		return shim.Error("Incorrect number of arguments. Expecting 4")
	}

	A = args[0]
	B = args[1]

	// Get the state from the ledger
	// TODO: will be nice to have a GetAllState call to ledger
	Avalbytes, err := stub.GetState(A)
	if err != nil {
		return shim.Error("Failed to get state")
	}
	if Avalbytes == nil {
		return shim.Error("Entity not found")
	}
	Aval, _ = strconv.Atoi(string(Avalbytes))

	Bvalbytes, err := stub.GetState(B)
	if err != nil {
		return shim.Error("Failed to get state")
	}
	if Bvalbytes == nil {
		return shim.Error("Entity not found")
	}
	Bval, _ = strconv.Atoi(string(Bvalbytes))

	// Perform the execution
	X, err = strconv.Atoi(args[2])
	if err != nil {
		return shim.Error("Invalid transaction amount, expecting a integer value")
	}

	Y, err = strconv.Atoi(args[3])
	if err != nil {
		return shim.Error("Invalid transaction amount, expecting a integer value")
	}

	Aval = Aval - X
	Bval = Bval + Y
	fmt.Printf("Aval = %d, Bval = %d\n", Aval, Bval)

	// Write the state back to the ledger
	err = stub.PutState(A, []byte(strconv.Itoa(Aval)))
	if err != nil {
		return shim.Error(err.Error())
	}

	err = stub.PutState(B, []byte(strconv.Itoa(Bval)))
	if err != nil {
		return shim.Error(err.Error())
	}

	return shim.Success([]byte("ok"))
}

// Deletes an entity from state
// arg:entity
func (t *SimpleChaincode) delete(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}

	A := args[0]

	// Delete the key from the state in ledger
	err := stub.DelState(A)
	if err != nil {
		return shim.Error("Failed to delete state")
	}

	return shim.Success([]byte("ok"))
}

// query callback representing the query of a chaincode
func (t *SimpleChaincode) query(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var A string // Entities
	var err error

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting name of the person to query")
	}

	A = args[0]

	// Get the state from the ledger
	Avalbytes, err := stub.GetState(A)
	if err != nil {
		jsonResp := "{\"Error\":\"Failed to get state for " + A + "\"}"
		return shim.Error(jsonResp)
	}

	if Avalbytes == nil {
		jsonResp := "{\"Error\":\"Nil amount for " + A + "\"}"
		return shim.Error(jsonResp)
	}

	jsonResp := "{\"Name\":\"" + A + "\",\"Amount\":\"" + string(Avalbytes) + "\"}"
	fmt.Printf("Query Response:%s\n", jsonResp)
	return shim.Success(Avalbytes)
}

func main() {
	err := shim.Start(new(SimpleChaincode))
	if err != nil {
		fmt.Printf("Error starting Simple chaincode: %s", err)
	}
}
