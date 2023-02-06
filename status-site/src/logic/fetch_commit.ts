import axios from "axios"
import type { Commit, ExtendedCommit } from "./github.commit.types"

export interface BareCommit {
  sha: string
  success: boolean
  log: string
}

export async function fetchCommits(
  bareCommits: BareCommit[]
): Promise<ExtendedCommit[]> {
  const commits = await axios.get<Commit[]>(
    `https://api.github.com/repos/kth-cdate-courses/DD2480-CI/commits`
  )
  // Only include commits that we have processed
  return commits.data.map((commit) => {
    const bareCommit = bareCommits.find(
      (bareCommit) => bareCommit.sha === commit.sha
    )
    if (bareCommit != null) {
      return {
        ...commit,
        success: bareCommit.success,
        log: bareCommit.log,
      } as ExtendedCommit
    }
    return commit as ExtendedCommit
  })
}
