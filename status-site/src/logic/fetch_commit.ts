import axios from "axios"
import type { Commit, ExtendedCommit } from "./github.commit.types"

export interface BareCommit {
  sha: string
  status: "success" | "fail" | "unset"
  log?: string
}

export async function fetchCommits(
  bareCommits: BareCommit[]
): Promise<ExtendedCommit[]> {
  const commits = await axios.get<Commit[]>(
    `https://api.github.com/repos/kth-cdate-courses/DD2480-CI/commits?sha=assessment`
  )
  // Only include commits that we have processed
  return commits.data.map((commit) => {
    const bareCommit = bareCommits.find(
      (bareCommit) => bareCommit.sha === commit.sha
    )
    return {
      ...commit,
      status: bareCommit?.status ?? "unset",
      log: bareCommit?.log,
    } as ExtendedCommit
  })
}
