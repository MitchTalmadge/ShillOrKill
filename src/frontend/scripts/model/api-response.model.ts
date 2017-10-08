/**
 * Top level response from all API requests.
 */
export interface APIResponse {

    ok: boolean,

    error: string,

    message: string,

    content: any

}