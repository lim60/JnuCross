// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract SimpleStorage {
    uint storedData=5;
    event value(uint storedData);

    function set(uint x) public returns(uint){
        storedData = x;
        emit value(storedData);
        return storedData;
    }

    function get() public view returns (uint retVal) {
        return storedData;
    }
}