// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract SimpleStorage {
    struct StoreEntity{
        uint storgeId;
        string data;
    }


    uint numstores;
    mapping (uint => StoreEntity) storeEntities;

    event valueStore(uint numstores, uint storgeId, string data);

    function set(uint x, string memory dataInput) public returns(uint, uint, string memory){
        uint index = numstores++;
        storeEntities[index].storgeId = x;
        storeEntities[index].data = dataInput;

        emit valueStore(index, storeEntities[index].storgeId, storeEntities[index].data);
        return (index, storeEntities[index].storgeId, storeEntities[index].data);
    }

    function get(uint index) public view returns (uint, string memory) {
        return (numstores, storeEntities[index].data);
    }
}