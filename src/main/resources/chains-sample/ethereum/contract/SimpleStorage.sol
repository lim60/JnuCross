pragma solidity ^0.4.0;

contract SimpleStorage {
    uint storedData;

    constructor() public {
        storedData = 5;
    }

    function set(uint x) public returns (uint retVal) {
        storedData = x;
        return storedData;
    }

    function get() public view returns (uint retVal) {
        return storedData;
    }
}
