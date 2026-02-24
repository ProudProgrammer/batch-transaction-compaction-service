# Batch Transaction Compaction
### Background
You are working on a Spring Boot–based payment processing system.

The system receives batches of transaction IDs from upstream services.
Each batch is represented as an integer array.

- A value of 0 represents an invalid or failed transaction
- A non-zero value represents a valid transaction

Before forwarding the batch to a downstream settlement service, the system must:

1. Process all valid transactions first
2. Move all invalid transactions (0) to the end
3. Preserve the original order of valid transactions
4. Perform the operation in-place to avoid extra memory usage

### Task
Implement a Spring Boot service that:

- Accepts a batch of transactions via a REST endpoint
- Reorders the batch according to the rules above
- Returns the processed batch

For example,
```
Request Body:
[0, 1045, 0, 2099, 3301]

Response:
[1045, 2099, 3301, 0, 0]
```