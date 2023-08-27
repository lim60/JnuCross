// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract SimpleStorage {
    struct StoreEntity{
        uint storgeId;
        string data;
    }

    constructor(){
        numstores=0;
    }

    uint numstores;
    mapping (uint => StoreEntity) storeEntities;

    event valueStore(uint numstores, StoreEntity storeEntity);

    function set(uint x, string memory dataInput) public returns(uint, uint, string memory){
        uint index = numstores++;
        storeEntities[index].storgeId = x;
        storeEntities[index].data = dataInput;

        emit valueStore(index, storeEntities[index]);
        return (index, storeEntities[index].storgeId, storeEntities[index].data);
    }

    function get(uint index) public view returns (uint, string memory) {
        return (numstores, storeEntities[index].data);
    }
}