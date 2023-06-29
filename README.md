# GPT-API 

## End Points
### GPT
### Method: POST
### URL: https://my-chatgpt-api.fly.dev/api/ask-me
### Authorization: Bearer token
### body:
``` 
{
    "question": "Tell me a joke" 
}

```
## Ok Response

```
{
    "status": number,
    "question": string,
    "response": string
}
```
## Error Response
```
{
    "status": number,
    "message": string
}
```
## ClearCache
### Method: GET
### URL: https://my-chatgpt-api.fly.dev/api/clear-cache
### Authorization: Bearer token

## Ok Response

```
{
    "status": number,
    "message": string,
}
```