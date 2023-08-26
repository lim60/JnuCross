// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract SimpleStorage {
    uint storedData;

    function set(uint x) public returns (uint retVal) {
        storedData = x;
        return storedData;
    }

    function get() public view returns (uint retVal) {
        return storedData;
    }
}